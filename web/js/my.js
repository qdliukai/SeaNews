// ----------------------------------------加载时执行函数------------------------------------------------------------------
// 系统默认刷新时显示实时推荐内容，此函数用户显示默认的实时推荐的时间范围，
// 当用户登录时，查询数据库显示用户最新的设置时间
// 当未登录时，显示默认最近24小时
$(function() {
    var userName = document.getElementById("userFlag").value;
    if (userName != null && userName != "") {
        jQuery.ajax({
            url: "selectTaskTypeLatest",
            type: "POST",
            data: {"userName": userName, "taskType": "online"},
            dataType: "json",
            success: function (res) {
                if (res.result === "true") {
                    if(res.timeRange === "24"){
                        document.getElementById("onlineSelect").options[0].selected = true;
                    }else if (res.timeRange === "12"){
                        document.getElementById("onlineSelect").options[1].selected = true;
                    } else {
                        document.getElementById("onlineSelect").options[2].selected = true;
                    }
                } else {
                    layer.alert("切换失败", {
                        title: "提示",
                        icon: 5,
                    });
                }
            },
            error: function (res) {
                layer.alert("切换失败", {
                    title: "提示",
                    icon: 5,
                });
            }
        });
    }else {
        document.getElementById("onlineSelect").options[0].selected = true;
    }
});

var word_array = [];
// 初始化用户兴趣标签的函数，用于词云的数据显示，加载时运行
$(function() {
    jQuery.ajax({
        url:"/getUserLabel",
        type:"POST",
        data:{"userName":"HighLab"},
        dataType:"json",
        success:function (res) {
            var userLaberMap = res;
            // 遍历从controller中获取的用户标签map，得到标签和对应权重，放入word_array数组。
            for (var key in userLaberMap){
                word_array.push({text:key, weight: parseFloat(userLaberMap[key])});
                // alert("data:" + key + userLaberMap[key]);
            }
        },
        error:function (res) {
            alert("出错了")
        }
    });
    // 将id为example的div赋予数据和属性，显示词云
    $("#example").jQCloud(word_array, {
        width: 300,
        height: 300,
        shape: "rectangular",
        removeOverflowing: true,
    });
});
// ------------------------------------用户登录相关函数--------------------------------------------------------------------
// 用户登录界面弹出函数
// 当用户点击登录按钮时，通过layer弹出用户登录div，loginBox。
function ShowLoginText() {
    layer.open({
        type: 1,
        title: '用户登录',
        maxmin: true,
        skin: 'layui-layer-lan',
        shadeClose: true, //点击遮罩关闭层    
        area: ['365px', '250px'],
        content: $("#loginBox")//弹框显示的url,对应的页面  
    });
}
// 用户登录函数
// 当用户在弹出的登录界面中输入完成后，点击登录按钮执行此函数
// 进行基础验证后，与controller交互，检查用户并返回是否登录成功。
function Login() {
    var username = $.trim($("#InputUsername").val());//获取用户名trim是去掉空格
    var password = $.trim($("#InputUserPwd").val());//获取密码
    if (username == "" || password == "") {
        layer.alert("用户名或者密码不能为空!", {
            title: "提示",
            icon: 5,
        });
    } else {
        jQuery.ajax({
            url: "checkUser",
            type: "POST",
            data: {"userName": username, "userPass": password},
            dataType: "json",
            success: function (res) {
                if (res.result === "true") {
                    $(window).attr("location", "/index");
                } else {
                    layer.alert("用户名或者密码错误!", {
                        title: "提示",
                        icon: 5,
                    });
                }
            },
            error: function (res) {
                alert("出错了");
            }
        });
    }
}
// 用户退出登录函数
// 当用户点击退出按钮时触发此函数，与controller交互，删除session中存储的用户信息。
function Logout() {
    jQuery.ajax({
        url: "userLogout",
        type: "POST",
        data: {},
        dataType: "json",
        success: function (res) {
            if (res.result === "true") {
                $(window).attr("location", "/index");
            } else {
                layer.alert("退出失败", {
                    title: "提示",
                    icon: 5,
                });
            }
        },
        error: function (res) {
            layer.alert("退出失败", {
                title: "提示",
                icon: 5,
            });
        }
    });
}
// ---------------------------------用户收藏相关函数-----------------------------------------------------------------------
// 弹出用户收藏界面函数
function userCollectEdit() {
    var userName = document.getElementById("userFlag").value;
    if (userName != null && userName != "") {
        jQuery.ajax({
            url: "collectEdit",
            type: "POST",
            data: {"userName": userName},
            dataType: "json",
            success: function (res) {
                if (res.result === "true") {
                    layer.open({
                        title: "用户收藏",
                        type: 2,
                        area: ['1100px', '500px'],
                        content: '/showCollect'
                    });
                    // $(window).attr("location","/showCollect");
                } else {
                    layer.alert("编辑失败", {
                        title: "提示",
                        icon: 5,
                    });
                }
            },
            error: function (res) {
                layer.alert("编辑失败", {
                    title: "提示",
                    icon: 5,
                });
            }
        });
    } else {
        layer.alert("用户尚未登录，请先登录", {
            title: "提示",
            icon: 5,
        });
    }
}
// 用户收藏资讯函数，当用户点击按钮收藏海洋资讯时触发
function userCollect(collectKey) {
    var userName = document.getElementById("userFlag").value;
    // var collectKey = document.getElementById("collectKey").value;
    if (userName != null && userName != "") {
        jQuery.ajax({
            url: "userCollect",
            type: "POST",
            data: {"userName": userName, "collectKey": collectKey},
            dataType: "json",
            success: function (res) {
                if (res.result === "true") {
                    layer.alert("收藏成功", {
                        title: "提示",
                        icon: 1,
                    });
                } else {
                    layer.alert("已收藏", {
                        title: "提示",
                        icon: 5,
                    });
                }
            },
            error: function (res) {
                layer.alert("收藏失败", {
                    title: "提示",
                    icon: 5,
                });
            }
        });
    } else {
        layer.alert("用户尚未登录，请先登录", {
            title: "提示",
            icon: 5,
        });
    }
}
// ------------------------------------------用户兴趣标签函数--------------------------------------------------------------
// 弹出用户兴趣标签编辑页面函数
function userLabelEdit() {
    var userName = document.getElementById("userFlag").value;
    if (userName != null && userName !== "") {
        jQuery.ajax({
            url: "labelEdit",
            type: "POST",
            data: {"userName": userName},
            dataType: "json",
            success: function (res) {
                if (res.result === "true") {
                    layer.open({
                        title: "用户标签",
                        type: 2,
                        area: ['700px', '500px'],
                        content: '/showLabel'
                    });
                    // $(window).attr("location","/showCollect");
                } else {
                    layer.alert("编辑失败", {
                        title: "提示",
                        icon: 5,
                    });
                }
            },
            error: function (res) {
                layer.alert("编辑失败", {
                    title: "提示",
                    icon: 5,
                });
            }
        });
    } else {
        layer.alert("用户尚未登录，请先登录", {
            title: "提示",
            icon: 5,
        });
    }
}
// --------------------------------------------推荐类型相关函数，离线和实时--------------------------------------------------
// 显示离线推荐函数
// 当用户点击离线推荐按钮时，显示离线推荐相关设置，并隐藏实时推荐
function showOffline() {
    document.getElementById("offline").style.display="block";
    document.getElementById("online").style.display="none";
    document.getElementById("offlineBtn").style.background = "#EC9D19"
    document.getElementById("onlineBtn").style.background = "#009dff"
    var userName = document.getElementById("userFlag").value;
    // 与controller交互，查询用户的离线推荐最新设置
    if (userName != null && userName != "") {
        jQuery.ajax({
            url: "selectTaskTypeLatest",
            type: "POST",
            data: {"userName": userName, "taskType": "offline"},
            dataType: "json",
            success: function (res) {
                if (res.result === "true") {
                    $('#beginTime').val(res.beginTime);
                    $('#endTime').val(res.endTime);
                } else {
                    layer.alert("切换失败", {
                        title: "提示",
                        icon: 5,
                    });
                }
            },
            error: function (res) {
                layer.alert("切换失败", {
                    title: "提示",
                    icon: 5,
                });
            }
        });
    } else {
        layer.alert("用户尚未登录，请先登录", {
            title: "提示",
            icon: 5,
        });
    }
}
// 显示实时推荐函数
// 当用户点击实时推荐按钮时，显示实时推荐相关设置，并隐藏离线推荐
function showOnline() {
    document.getElementById("offline").style.display="none";
    document.getElementById("online").style.display="block";
    document.getElementById("offlineBtn").style.background = "#009dff"
    document.getElementById("onlineBtn").style.background = "#EC9D19"
    var userName = document.getElementById("userFlag").value;
    // 与controller交互，查询用户的实时推荐最新设置
    if (userName != null && userName != "") {
        jQuery.ajax({
            url: "selectTaskTypeLatest",
            type: "POST",
            data: {"userName": userName, "taskType": "online"},
            dataType: "json",
            success: function (res) {
                if (res.result === "true") {
                    if(res.timeRange === "24"){
                        document.getElementById("onlineSelect").options[0].selected = true;
                    }else if (res.timeRange === "12"){
                        document.getElementById("onlineSelect").options[1].selected = true;
                    } else {
                        document.getElementById("onlineSelect").options[2].selected = true;
                    }
                } else {
                    layer.alert("切换失败", {
                        title: "提示",
                        icon: 5,
                    });
                }
            },
            error: function (res) {
                layer.alert("切换失败", {
                    title: "提示",
                    icon: 5,
                });
            }
        });
    } else {
        layer.alert("用户尚未登录，请先登录", {
            title: "提示",
            icon: 5,
        });
    }
}
// 离线推荐有时间设置选择框和确定按钮，当用户时间设置完毕后，点击确定按钮触发此函数。
// 将用户的设置插入数据库中，并触发相应的命令运行spark程序，开启离线推荐
function startOfflineRecommend() {
    var beginTimeStr = $("#beginTime").val();
    var endTimeStr = $("#endTime").val();
    var userName = document.getElementById("userFlag").value;
    if (userName != null && userName != "") {
        if (beginTimeStr === "" || endTimeStr === "" || beginTimeStr > endTimeStr) {
            layer.alert("时间有误，请重新检查", {
                title: "提示",
                icon: 5,
            });
        }else {
            jQuery.ajax({
                url: "recommendType",
                type: "POST",
                data: {"userName": userName, "taskType": "offline","timeRange": beginTimeStr + " " + endTimeStr},
                dataType: "json",
                success: function (res) {
                    if (res.result === "true") {
                        layer.alert("查询成功", {
                            title: "提示",
                            icon: 1,
                        });
                    } else {
                        layer.alert("查询失败", {
                            title: "提示",
                            icon: 5,
                        });
                    }
                },
                error: function (res) {
                    layer.alert("查询失败", {
                        title: "提示",
                        icon: 5,
                    });
                }
            });
        }
    } else {
        layer.alert("用户尚未登录，请先登录", {
            title: "提示",
            icon: 5,
        });
    }
}
// 实时推荐为下拉选择框
// 将用户的设置插入数据库中
function startOnlineRecommend() {
    var userName = document.getElementById("userFlag").value;
    var onlineTimeSelected = document.getElementById("onlineSelect").value;
    if (userName != null && userName != "") {
        jQuery.ajax({
            url: "recommendType",
            type: "POST",
            data: {"userName": userName, "taskType": "online","timeRange": onlineTimeSelected},
            dataType: "json",
            success: function (res) {
                if (res.result === "true") {
                    layer.alert("切换成功", {
                        title: "提示",
                        icon: 1,
                    });
                } else {
                    layer.alert("切换失败", {
                        title: "提示",
                        icon: 5,
                    });
                }
            },
            error: function (res) {
                layer.alert("切换失败", {
                    title: "提示",
                    icon: 5,
                });
            }
        });
    } else {
        layer.alert("用户尚未登录，请先登录", {
            title: "提示",
            icon: 5,
        });
    }
}

// $("#startTime").bind("input propertychange", function(){
//     console.log($(this).val());
// });
// $("#endTime").bind("input propertychange", function(){
//     console.log($(this).val());
// });