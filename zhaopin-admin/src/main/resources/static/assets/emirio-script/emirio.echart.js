/**
 * emirio.echart.js
 * 
 * Description: 
 *  main feature is to show some data informatioin by echart
 * 
 * Dependency:
 *  echarts.min.js@4.8.0
 * 
 * @author emirio
 * @date 2020/6/24
 * @version v1.2
 * @since v1.0
 */

// get default echart element
var genderChart = echarts.init(document.getElementById('sex-chart'));
var employeeChart = echarts.init(document.getElementById('employee-chart'));
var areaChart = echarts.init(document.getElementById('area-chart'));

// store echart option
var genderOption;
var empOption;
var areaOption;

/**
 * initialize and show the default gender echart
 */
$(function() {
    // add listener to windows resize event
    window.addEventListener('resize', () => {
        // if windows were resized, then trigger echart resize listener
        genderChart.resize();
        employeeChart.resize();
        areaChart.resize();
    });

    // load and show the default echart data
    $.ajax({
        type: "GET",
        url: "/employee/groupBySex",
        dataType: "json",
        success: function(data) {
            let gender = [];
            let total = [];

            for(let i = 0; i < data.length; i++) {
                gender[i] = data[i].sex;
                total[i] = data[i].total;
            }
            // set default gender optioin
            genderOption = {
                title: {
                    text: '性别总比例',
                    // subtext: '统计男女性别'
                },
                tooltip: {
                    show: true,
                },
                textStyle: {
                    color: 'black'
                },
                legend: {
                    data: '人数',
                },
                xAxis: {
                    name: '性别',
                    data: gender,
                    type: 'category'        
                },  
                yAxis: {
                    name: '人数',
                    type: 'value',
                    axisLabel: {
                        formatter: '{value}',
                    }
                },
                toolbox: {
                    show: true,
                    feature: {
                        dataView: {readOnly: false},    // enable dataview
                        restore: {},    // show restore icon
                        saveAsImage: {},    // show download icon
                    }
                },
                series: [{
                    name: '人数',
                    type: 'bar',
                    data: total,
                    label: { // set label on the top of bar
                        normal: {
                            show: true,
                            position: 'top',
                            color: function(param) {
                                var color = ['#262398', '#7c0e51', '#cec03b'];
                                return color[param.dataIndex];
                            }
                        },
                    },
                    itemStyle: {
                        normal: { // normal bar color
                            color: function(param) {
                                var color = ['#262398', '#7c0e51', '#cec03b'];
                                return color[param.dataIndex];
                            }
                        },
                        emphasis: { // highlight bar color
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }]
            };

            // set sex-chart option
            genderChart.setOption(genderOption);
        }
    });

    $.ajax({
        type: "GET",
        url: "/employee/groupByEmployee",
        dataType: "json",
        success: function (data) {
            var job = [];
            var total = [];
            var seriesData = [];
            var selectedData = {};

            for (let i = 0; i < data.length; i++) {
                job[i] = data[i].job;
                total[i] = data[i].total; 
                seriesData.push({
                    name: job[i],
                    value: data[i].total
                });
                // set legend selected field
                selectedData[job[i]] = i < 10;
            }

            empOption = {
                title: {
                    text: '职位统计',
                },
                tooltip: {
                    trigger: 'item',
                    formatter: '{b} : {c} ({d}%)'
                },
                toolbox: {
                    show: true,
                    feature: {
                        restore: {},    // show restore icon
                        saveAsImage: {},    // show download icon
                    }
                },
                legend: {
                    type: 'scroll',
                    orient: 'vertical',
                    right: 10,
                    top: 40,
                    bottom: 20,
                    data: job,
                    selected: selectedData
                },
                series: [
                    {
                        name: job,
                        type: 'pie',
                        radius: '55%',
                        center: ['40%', '50%'],
                        data: seriesData,
                        emphasis: {
                            itemStyle: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
            };
            employeeChart.setOption(empOption);
        }
    });

    $.ajax({
        type: "GET",
        url: "/employee/groupByArea",
        dataType: "json",
        success: function (data) {
            var mapData = [];
            for (let i = 0; i < data.length; i++) {
                mapData.push({
                    // if province contains '省', then drop it
                    name: data[i].province.replace('省', ''),
                    value: data[i].total
                });
            }
            // get map area option
            areaOption = {
                title: {
                    text: '员工住址分布图',
                    x: 'center'
                },
                tooltip: {
                    trigger: 'item',
                },
                toolbox: {
                    show: true,
                    feature: {
                        dataView: {readOnly: false},    // enable dataview
                        restore: {},    // show restore icon
                        saveAsImage: {},    // show download icon
                    }
                },
                visualMap: {  
                    show : true,  

                    // show area map with color range 
                    // min: 8000,
                    // max: 10000,
                    // calculable: true,
                    // color: ['#d94e5d','#eac736','#50a3ba'],

                    // show area map with color block
                    splitList: [
                        {start: 0, end: 8000}, {start: 8000, end: 9000}, 
                        {start: 9000, end: 9100}, {start: 9100, end: 9200},
                        {start: 9200, end: 9300}, {start: 9300, end: 9400},
                        {start: 9400, end: 9500}, {start: 9500, end: 10000},
                    ],
                }, 
                series: [{  
                    name: '人数',  
                    type: 'map', 
                    data: mapData, 
                    mapType: 'china',   
                    roam: true,  
                    label: {  
                        normal: {  
                            show: true
                        },  
                        emphasis: {  
                            show: false  
                        }  
                    },  
                }] 
            }

            areaChart.setOption(areaOption);
        }
    });
});

/**
 * check button click event by customUrl
 */
$(document).on("click", function(event) {
    var btnEvent = event.target;
    // bind echart with click event
    switch (btnEvent.className) {
        case 'btn btn-primary btn-number':
            genderChart.clear(); // clear() solves that line and bar charts with dirty problem when change to another optional chart
            genderChart.setOption(genderOption);
            // xxxOption is stored as global object, when page loaded, so prevent request again by setOption
            // setOption("/employee/groupBySex");
            break;
        case 'btn btn-warning btn-age':
            genderChart.clear();
            setOption("/employee/groupByAge");
            break;
        case 'btn btn-success btn-gender':
            genderChart.clear();
            setOption("/employee/groupByRange");
            break;
        case 'btn btn-primary btn-job':
            employeeChart.clear();
            employeeChart.setOption(empOption);
            break;
        case 'btn btn-warning btn-dept':
            employeeChart.clear();
            setOption("/employee/groupByDept");
            break;
        case 'btn btn-primary btn-area':
            areaChart.clear();
            areaChart.setOption(areaOption);
            break;
        case 'btn btn-success btn-annual-salary':
            areaChart.clear();
            setOption("/employee/groupByAnnual");
            break;
        case 'btn btn-warning btn-salary':
            areaChart.clear();
            setOption("/employee/groupBySalary");
            break;
        default:
            break;
    }
});

/**
 * set genderChart option with different url
 * data from back-end changes with different url
 * 
 * @param {*} customUrl 
 */
var setOption = function(customUrl) {
    $.ajax({
        type: "GET",
        url: customUrl,
        dataType: "json",
        success: function(jsonData) {
            // store jsonData
            data = jsonData;

            // Do not use switch case to replace if-else, 
            // block-variable [let] will cause some unexpected error
            // [let] in switch-case is case-block scope, be careful
            if(customUrl == '/employee/groupBySex') {
                return genderOption;
                // prevent request again
                // genderChart.setOption(genderOption);
            } else if(customUrl == "/employee/groupByAge") {
                let age = [];
                let male = [];
                let female = [];
                let secret = [];
        
                for(let i = 0; i < data.length; i++) {
                    age[i] = data[i].age;
                    male[i] = data[i].male;
                    female[i] = data[i].female;
                    secret[i] = data[i].secret;
                }
                // set ageOption
                ageOption = {
                    title: {
                        text: '年龄性别占比',
                    },
                    tooltip: {
                        show: true,
                    },
                    textStyle: {
                        color: 'black'
                    },
                    legend: {
                        data: ['男', '女', '保密'],
                    },
                    xAxis: {
                        name: '年龄',
                        data: age,
                        type: 'category'        
                    },  
                    yAxis: {
                        name: '人数',
                        type: 'value',
                        axisLabel: {
                            formatter: '{value}',
                        }
                    },
                    toolbox: {
                        show: true,
                        feature: {
                            dataZoom: {     // enable area data zoom
                                yAxisIndex: 'none',
                            },
                            dataView: {readOnly: false},    // enable dataview
                            magicType: {type: ['line', 'bar', 'stack']}, // enable line and bar type
                            restore: {},    // show restore icon
                            saveAsImage: {},    // show download icon
                        }
                    },
                    series: [
                        {
                            name: '男',
                            type: 'bar',
                            data: male,
                            color: '#262398',
                            markPoint: {
                                label: {
                                    color: '#fff'
                                },
                                data: [
                                    {type: 'max', name: '最大值'},
                                    {type: 'min', name: '最小值'}
                                ]
                            },
                            // markLine: {
                            //     data: [ {type: 'average', name: '平均值'}]
                            // },
                            itemStyle: {
                                emphasis: { // highlight bar color
                                    shadowBlur: 3,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.8)'
                                }
                            }
                        },
                        {
                            name: '女',
                            type: 'bar',
                            data: female,
                            color: '#7c0e51',
                            markPoint: {
                                label: {
                                    color: '#fff'
                                },
                                data: [
                                    {type: 'max', name: '最大值'},
                                    {type: 'min', name: '最小值'}
                                ]
                            },
                            // markLine: {
                            //     data: [ {type: 'average', name: '平均值'}]
                            // },
                            itemStyle: {
                                emphasis: { 
                                    shadowBlur: 3,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.8)'
                                }
                            }
                        },
                        {
                            name: '保密',
                            type: 'bar',
                            data: secret,
                            color: '#cec03b',
                            markPoint: {
                                label: {
                                    color: '#fff'
                                },
                                data: [
                                    {type: 'max', name: '最大值'},
                                    {type: 'min', name: '最小值'}
                                ]
                            },
                            // markLine: {
                            //     data: [ {type: 'average', name: '平均值'}]
                            // },
                            itemStyle: {
                                emphasis: { 
                                    shadowBlur: 3,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.8)'
                                }
                            }
                        }
                    ]
                };
                genderChart.setOption(ageOption);
            } else if(customUrl == "/employee/groupByRange") {
                let age = [];
                let male = [];
                let female = [];
                let secret = [];
        
                for(let i = 0; i < data.length; i++) {
                    age[i] = data[i].age_range;
                    male[i] = data[i].male;
                    female[i] = data[i].female;
                    secret[i] = data[i].secret;
                }
                // set rangeOption
                rangeOption = {
                    title: {
                        text: '年龄段性别占比',
                    },
                    tooltip: {
                        show: true,
                    },
                    textStyle: {
                        color: 'black'
                    },
                    legend: {
                        data: ['男', '女', '保密'],
                    },
                    xAxis: {
                        name: '年龄',
                        data: age,
                        type: 'category'        
                    },  
                    yAxis: {
                        name: '人数',
                        type: 'value',
                        axisLabel: {
                            formatter: '{value}',
                        }
                    },
                    toolbox: {
                        show: true,
                        feature: {
                            dataZoom: {     // enable area data zoom
                                yAxisIndex: 'none',
                            },
                            dataView: {readOnly: false},    // enable dataview
                            magicType: {type: ['line', 'bar', 'stack']}, // enable line and bar type
                            restore: {},    // show restore icon
                            saveAsImage: {},    // show download icon
                        }
                    },
                    series: [
                        {
                            name: '男',
                            type: 'bar',
                            data: male,
                            color: '#262398',
                            label: { // set label on the top of bar
                                normal: {
                                    show: true,
                                    position: 'top',
                                    color: '#262398',
                                    fontSize: 10,
                                },
                            },
                            itemStyle: {
                                emphasis: { // highlight bar color
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        },
                        {
                            name: '女',
                            type: 'bar',
                            data: female,
                            color: '#7c0e51',
                            label: { 
                                normal: {
                                    show: true,
                                    position: 'top',
                                    color: '#7c0e51',
                                    fontSize: 10,
                                },
                            },
                            itemStyle: {
                                emphasis: { 
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        },
                        {
                            name: '保密',
                            type: 'bar',
                            data: secret,
                            color: '#cec03b',
                            label: { 
                                normal: {
                                    show: true,
                                    position: 'top',
                                    color: '#cec03b',
                                    fontSize: 10,
                                },
                            },
                            itemStyle: {
                                emphasis: { 
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                };
                genderChart.setOption(rangeOption);
            } else if(customUrl == "/employee/groupByDept") {
                let name = [];
                let total = [];
                let seriesData = [];

                for (let i = 0; i < data.length; i++) {
                    name[i] = data[i].name;
                    total[i] = data[i].total; 
                    seriesData.push({
                        name: name[i],
                        value: total[i]
                    })                   
                }
                
                deptOption = {
                    title: {
                        text: '部门员工统计',
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: '{b} : {c} ({d}%)'
                    },
                    // legend: {
                    //     type: 'scroll',
                    //     orient: 'vertical',
                    //     right: 10,
                    //     top: 20,
                    //     botton: 20,
                    //     data: name
                    // },
                    toolbox: {
                        show: true,
                        feature: {
                            mark: {show: true},
                            dataView: {show: true, readOnly: false},
                            magicType: {
                                show: true,
                                type: ['pie', 'funnel']
                            },
                            restore: {show: true},
                            saveAsImage: {show: true}
                        }
                    },
                    series: [
                        {
                            name: ['部门', '数量'],
                            type: 'pie',
                            radius: [30, 80],
                            // center: ['40%', '50%'],
                            roseType: 'area',
                            data: seriesData,
                        }
                    ]
                }

                employeeChart.setOption(deptOption);
            } else if(customUrl == "/employee/groupBySalary") {
                let id = [];
                let name = [];
                let max = [];
                let min = [];

                for (let i = 0; i < data.length; i++) {
                    id[i] = data[i].id;
                    name[i] = data[i].name;
                    max[i] = data[i].max;
                    min[i] = data[i].min;
                }

                salaryOption = {
                    title: {
                        text: '部门最高/最低/平均薪资图',
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow',
                            label: {
                                shadow: true
                            }
                        }
                    },
                    legend: {
                        data: ['最高薪', '最低薪']
                    },
                    toolbox: {
                        show: true,
                        feature: {
                            dataView: {show: true, readOnly: false},
                            magicType: {show: true, type: ['line', 'bar']},
                            restore: {show: true},
                            saveAsImage: {show: true}
                        }
                    },
                    calculable: true,
                    // avoid xAxis label out bound of border
                    grid: {
                        left: '10%', 
                        bottom: '20%'
                    },
                    xAxis: [
                        {
                            // name: '部门名称',
                            // rotate degree of axis label
                            axisLabel: {
                                interval: 0,
                                rotate: 40
                            },
                            type: 'category',
                            data: name, 
                            nameTextStyle: {
                                fontSize: 10
                            },

                        }
                    ],
                    yAxis: [
                        {
                            name: 'RMB/元',
                            type: 'value',
                            axisLabel: {
                                formatter: '{value}元'
                            }
                        }
                    ],
                    series: [
                        {
                            name: '最高薪',
                            type: 'bar',
                            data: max,
                            markPoint: {
                                data: [
                                    {type: 'max', name: '最大值'},
                                    {type: 'min', name: '最小值'}
                                ]
                            },
                            markLine: {
                                data: [
                                    {type: 'average', name: '平均值'}
                                ]
                            }
                        },
                        {
                            name: '最低薪',
                            type: 'bar',
                            data: min,
                            markPoint: {
                                data: [
                                    {type: 'max', name: '最大值'},
                                    {type: 'min', name: '最小值'}
                                ]
                            },
                            markLine: {
                                data: [
                                    {type: 'average', name: '平均值'}
                                ]
                            }
                        }
                    ]
                };
             
                areaChart.setOption(salaryOption);
            } else if(customUrl == "/employee/groupByAnnual") { 
                var dataMap = {};
                let objOptions = [];

                // store all data keys
                let yearData = Object.keys(data);

                // convert string array to number type
                // ['2015', '2016', ...] => [2015, 2016, ...]
                let yearNumberData = yearData.map(Number);

                // store max and min year from data keys
                let maxYear = Math.max.apply(null, yearNumberData);
                let minYear = Math.min.apply(null, yearNumberData);

                function dataFormatter(obj) {
                    var pList = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];
                    var temp;
                    for (var year = minYear; year <= maxYear; year++) {
                        var max = 0;
                        var sum = 0;
                        temp = obj[year];
                        for (var i = 0, l = temp.length; i < l; i++) {
                            max = Math.max(max, temp[i]);
                            sum += temp[i];
                            obj[year][i] = {
                                name: pList[i],
                                value: temp[i]
                            };
                        }
                        obj[year + 'max'] = Math.floor(max / 100) * 100;
                        obj[year + 'sum'] = sum;
                    }
                    return obj;
                }
                
                // get obj.series.data 
                dataMap.data = dataFormatter(data);

                for (let i = minYear; i <= maxYear; i++) {
                    // initialize all objects when loop start, Array.push store address, not value
                    let dataObj = {};
                    let seriesObj = {};
                    let titleObj = {};
                    let tmpObj = {};
                    let tmpNewObj = {};
                    /**
                     * store objOption as follows
                     * {
                     *     title: {text: '2002全国宏观经济指标'},
                     *     series: {data: Array[]},
                     *   },
                     */

                     // store title
                    titleObj.text = i + '年每月的薪资统计';

                    // store series
                    dataObj.data = dataMap.data[i];
                    seriesObj.series = dataObj;

                    // store series and title in a temporary object
                    tmpObj.title = titleObj;
                    tmpNewObj = Object.assign({}, tmpObj, seriesObj);
                    
                    // push store object's address not value, so object should be re-initialized,
                    // Array's element will be replace by the last value
                    objOptions.push(tmpNewObj);
                }

                salaryOption = {
                    baseOption: {
                        timeline: {
                            axisType: 'category',
                            // realtime: false,
                            // loop: false,
                            autoPlay: true,
                            // currentIndex: 2,
                            playInterval: 2000,
                            // controlStyle: {
                            //     position: 'left'
                            // },
                            data: yearData
                        },
                        title: {
                        },
                        tooltip: {
                        },
                        toolbox: {
                            show: true,
                            feature: {
                                // dataZoom: {     // enable area data zoom
                                //     yAxisIndex: 'none',
                                // },
                                dataView: {readOnly: false},    // enable dataview
                                magicType: {type: ['line', 'bar']}, // enable line and bar type
                                restore: {},    // show restore icon
                                saveAsImage: {},    // show download icon
                            }
                        },
                        legend: {
                            left: 'right',
                            data: '总薪资',
                        },
                        calculable : true,
                        grid: {
                            top: 80,
                            bottom: 100,
                            tooltip: {
                                trigger: 'axis',
                                axisPointer: {
                                    type: 'shadow',
                                    label: {
                                        show: true,
                                    }
                                }
                            }
                        },
                        xAxis: [
                            {
                                name: '月份',
                                type: 'category',
                                axisLabel: {'interval':0},
                                data: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12],
                                splitLine: {show: false}
                            }
                        ],
                        yAxis: [
                            {
                                type: 'value',
                                name: 'RMB/元'
                            }
                        ],
                        series: [{
                            name: '总薪资',
                            type: 'bar',
                            label: {
                                normal: {
                                    show: true,
                                    position: 'top'
                                }
                            }
                        }]
                    },
                    options: objOptions
                };

                areaChart.setOption(salaryOption);
            }
        }
    });

    
}
