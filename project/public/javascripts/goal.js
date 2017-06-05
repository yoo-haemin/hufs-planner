var arr;
var myJSON;
var dayOfWeekArr = ["#checkMon", "#checkTue", "#checkWed", "#checkThu", "#checkFri"]
var areaOfClassArr = ["#common_1", "#common_2", "#common_3", "#common_4", "#common_5", "#common_6", "#common_7", "#common_8", "#special_1", "#special_2", "#special_3", "#special_4", "#special_5", "#special_6", "#special_7", "#special_8", "#special_9"]
var ArrOfAllButton = ["#checkMon", "#checkTue", "#checkWed", "#checkThu", "#checkFri","#common_1", "#common_2", "#common_3", "#common_4", "#common_5", "#common_6", "#common_7", "#common_8", "#special_1", "#special_2", "#special_3", "#special_4", "#special_5", "#special_6", "#special_7", "#special_8", "#special_9"]

var ArrOfAllButton_button={
    //요일
    "#checkMon": ["Mon"],
    "#checkTue": ["Tue"],
    "#checkWed": ["Wed"],
    "#checkThu": ["Thu"],
    "#checkFri": ["Fri"],
    //공통교양
    "#common_1": ["인문강좌"],
    "#common_2": ["교양외국어"],
    "#common_3": ["실용외국어(선택)"],
    "#common_4": ["언어와문학"],
    "#common_5": ["문화와예술"],
    "#common_6": ["역사와철학"],
    "#common_7": ["국가와사회"],
    "#common_8": ["과학과기술"],
    //"#common_9": ["핵심인문기초"],
    //특수교양
    "#special_1": ["신입생세미나"],
    "#special_2": ["HUFS CAREER"],
    "#special_3": ["학습포트폴리오"],
    "#special_4": ["건강과레포츠"],
    "#special_5": ["특별교양"],
    "#special_6": ["외국인을 위한 한국학"],
    "#special_7": ["공동교양(한예종)"],
    "#special_8": ["군사학"],
    "#special_9": ["핵심인문기초"]
}

var dayOfWeek_day = {
    "#checkMon": ["Mon"],
    "#checkTue": ["Tue"],
    "#checkWed": ["Wed"],
    "#checkThu": ["Thu"],
    "#checkFri": ["Fri"]
}

var areaOfClass_area = {
    "#common_1": ["인문강좌"],
    "#common_2": ["교양외국어"],
    "#common_3": ["실용외국어(선택)"],
    "#common_4": ["언어와문학"],
    "#common_5": ["문화와예술"],
    "#common_6": ["역사와철학"],
    "#common_7": ["국가와사회"],
    "#common_8": ["과학과기술"],
   // "#common_9": ["핵심인문기초"],

    "#special_1": ["신입생세미나"],
    "#special_2": ["HUFS CAREER"],
    "#special_3": ["학습포트폴리오"],
    "#special_4": ["건강과레포츠"],
    "#special_5": ["특별교양"],
    "#special_6": ["외국인을 위한 한국학"],
    "#special_7": ["공동교양(한예종)"],
    "#special_8": ["군사학"],
    "#special_9": ["핵심인문기초"]
}

/****Remove Duplicates from Array****/
function uniq(a) {
    var prims = {"boolean":{}, "number":{}, "string":{}}, objs = [];

    return a.filter(function(item) {
        var type = typeof item;
        if(type in prims)
            return prims[type].hasOwnProperty(item) ? false : (prims[type][item] = true);
        else
            return objs.indexOf(item) >= 0 ? false : objs.push(item);
    });
}

/********checkbox click actions**************/
$(document).ready(
    function(){
        //json파일 읽어오기
        $.getJSON("/assets/json/2017-01.json", function(json){
            myJSON=json;
            //myJSON에 교양과목만 집어넣기
            myJSON = myJSON.filter(function (weekday) {
                return !weekday.hasOwnProperty("majorName2")
            })
            //console.log(myJSON);
        })

        //전체버튼 클릭 시
        $("#checkAll").change(function () {
            $(".col-md-12 .btn-week input:checkbox").prop('checked', $(this).prop("checked"));
        });

        $("#checkAll_2").change(function () {
            $(".col-md-12 .btn-area input:checkbox").prop('checked', $(this).prop("checked"));
        });

        $("#checkAll_3").change(function () {
            $(".col-md-12 .btn-special input:checkbox").prop('checked', $(this).prop("checked"));
        });
        //checkbox클릭시 동작
        $(".checkbox_click").change(function(){

            //기존의 checkbox들 싹 지우기
            $("#select_non_major").empty();
            $("#select_non_major").append("<legend>교양과목을 선택하세요</legend>");

            var ArrOfClickedButton=[];
            ArrOfAllButton.forEach(function(allButton){
                if($(allButton).prop('checked')){
                    ArrOfClickedButton.push(allButton)
                }
            })
            var newArr_Total=[];
            var newArr1=[]; //요일 배열
            var newArr2=[]; //영역 배열
            ArrOfClickedButton.forEach(function(clickedButton){
                var newArrtemp1=[];
                var newArrtemp2=[];
                //요일 버튼이 클릭되어 있을 때 반응
                if(jQuery.inArray(clickedButton, dayOfWeekArr) !== -1){
                    newArrtemp1 = myJSON.filter(function (buttonCondition){
                        return buttonCondition.courseTime.hasOwnProperty(ArrOfAllButton_button[clickedButton])
                    })
                }//영역 버튼이 클릭되어 있을 때 반응
                else if(jQuery.inArray(clickedButton, areaOfClassArr) !== -1){
                    newArrtemp2= myJSON.filter(function (buttonCondition) {
                        return buttonCondition.courseType.includes(ArrOfAllButton_button[clickedButton])
                    })
                }
                newArr1=newArr1.concat(newArrtemp1);
                newArr2=newArr2.concat(newArrtemp2);
            })

            ArrOfClickedButton.forEach(function(clickedButton){
                //요일 버튼이 클릭되어 있을 때 반응
                if(jQuery.inArray(clickedButton, dayOfWeekArr) !== -1){
                    newArr2 = newArr2.filter(function (buttonCondition){
                        return buttonCondition.courseTime.hasOwnProperty(ArrOfAllButton_button[clickedButton])
                    })
                    //영역 버튼이 클릭되어 있을 때 반응
                }else if(jQuery.inArray(clickedButton, areaOfClassArr) !== -1){
                    newArr1= newArr1.filter(function (buttonCondition) {
                        return buttonCondition.courseType.includes(ArrOfAllButton_button[clickedButton])
                    })
                }
            })

            newArr_Total=newArr1.concat(newArr2);


            //check되지 않은 요일들만 모아둘 배열
            var ArrOfUnClickedButton = ["#checkMon", "#checkTue", "#checkWed", "#checkThu", "#checkFri"];
            ArrOfClickedButton.forEach(function(clickedButton){
                if(jQuery.inArray(clickedButton, dayOfWeekArr) !== -1){
                    ArrOfUnClickedButton= ArrOfUnClickedButton.filter(function(unclicked){
                        return unclicked != clickedButton;
                    })
                }
            })

            ArrOfClickedButton.forEach(function(clickedButton){
                if(jQuery.inArray(clickedButton, dayOfWeekArr) !== -1){
                    newArr_Total=newArr_Total.filter(function(buttonCondition){
                        return buttonCondition.courseTime.hasOwnProperty(ArrOfAllButton_button[clickedButton])
                    })
                }
            })



            newArr_Total.forEach(function(suchCourse){
                var dayOfWeek = Object.keys(suchCourse.courseTime);

                var dayAndtime=[];
                dayOfWeek.forEach(function(weekday){
                    var timeOfWeek= suchCourse.courseTime[weekday].time;
                    var timeOfWeek_time=timeOfWeek.join();
                    dayAndtime.push("  [" + weekday + timeOfWeek_time+"]");
                })

                return $("#select_non_major").append("<div class='col-md-6 col_m'><input type='checkbox' name='courseNo[]' value='"+  suchCourse.courseNo + "'>" + suchCourse.courseName1 + "(" + dayAndtime +")" + "</div>")

            })


        })


    }
)

