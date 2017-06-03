#!/usr/bin/env node
/*
 * Simple script to convert json files, move courses to top level
*/
const fs = require('fs')

//// Function declaration begin
function convert(semester) {
    // Param maj: major OR liberal arts area
    // Return: all courses in major, converted
    const convertMajor = maj => maj.courses.reduce((acc, c) => {
        c.year = semester.year
        c.semester = semester.semester
        c.majorName1 = maj.name1? maj.name1: maj.name
        c.majorName2 = maj.name2
        c.majorCode = maj.code
        return acc.concat(c)
    }, [])

    return semester.major.concat(semester.liberalArts).reduce((acc, m) => acc.concat(convertMajor(m)), [])
}
//// Function declaration end


//// Script begin
const inDir = 'course-json'
const outDir = 'converted-json'

const files = fs.readdirSync(inDir)
const semesters = files.map(f => JSON.parse(fs.readFileSync(inDir + '\/' + f).toString()))

semesters.forEach(s => {
    console.log("converting " + s.year + "-0" + s.semester)
    const converted = convert(s)
    const json = JSON.stringify(convert(s), null, 2)
    const filename = outDir + "/" + s.year + "-0" + s.semester + ".json"
    fs.writeFileSync(filename, json)
})
//// Script End
