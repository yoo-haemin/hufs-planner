#!/usr/bin/env node
/*
 * Simple script to extract properties for sql
 * Generate random uuid using
scala -e "println((1 to 10000).map(_ => java.util.UUID.randomUUID).mkString(\"\", \"\n\", \"\"))" > randomUUID.txt
*/
const fs = require('fs')

////Function declaration begin
const getData = (f) => {
    //Helper function for array equality
    //https://stackoverflow.com/questions/3115982/
    const arraysEqual = (a, b) => {
        if (a === b) return true;
        if (a == null || b == null) return false;
        if (a.length != b.length) return false;
        for (var i = 0; i < a.length; ++i) {
            if (a[i] !== b[i]) return false;
        }
        return true;
    }

    //Helper function for flattening arrays
    //https://stackoverflow.com/questions/10865025
    //Modified to prevent deep flattening
    const flatten = function(arr, result = []) {
        for (let i = 0, length = arr.length; i < length; i++) {
            const value = arr[i]
            if (Array.isArray(value)) {
                for (let i = 0, length = value.length; i < length; i++) {
                    result.push(value[i])
                }
            } else {
                result.push(value)
            }
        }
        return result
    }

    const files = fs.readdirSync(dirname)
    const semesters = files.map(f => JSON.parse(fs.readFileSync(dirname + '\/' + f).toString()))
    const allCourses =
          flatten(semesters.reduce(
              (acc, s) =>
                  acc.concat(
                      s.major.map(m => m.courses.map(c => f(c))),
                      s.liberalArts.map(m => m.courses.map(c => f(c))))
              , []))

    return allCourses.reduce(
        (acc, f) =>
            acc.findIndex(field => Array.isArray(field) ? arraysEqual(field, f) : field === f) >= 0
            ? acc
            : acc.concat(Array.isArray(f)? [f]: f), [])
}

const extractCourseType = c => c.courseType
const extractProfessor =
      c =>
      c.professorNameAdditional === undefined || c.professorNameAdditional === '-'
      ? [c.professorNameMain, ""]
      : [c.professorNameMain, c.professorNameAdditional]
//// Function declaration end


//// Script begin
let fn = process.argv[2]
switch (fn) {
case `1`:
    fn = extractCourseType
    break
case `2`:
    fn = extractProfessor
    break
default :
    console.log("\nUsage: <option> <dirname>\n\n<option>\n(1) Course Types\n(2) Professor List\n\n<dirname>\nThe directory that contains ONLY the json files from course-crawler\n\n")
    process.exit(-1)
}

const dirname = process.argv[3]
const files = fs.readdirSync(dirname)
const semesters = files.map(f => JSON.parse(fs.readFileSync(dirname + '\/' + f).toString()))
const data = JSON.stringify(getData(fn), null, 2)

console.log(data)
//// Script End
