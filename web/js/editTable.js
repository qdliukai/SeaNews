//需要首先通过Jq来解决内容部分奇偶行的背景色不同
$(document).ready(function () {
    //找到表格的内容区域中所有的奇数行
    //使用even是为了把通过tbody tr返回的所有tr元素中，
    //在数组里面下标是偶数的元素返回，因为这些元素，
    //实际上才是我们期望的tbody里面的奇数行
    $("tbody tr:odd").css("background-color", "#EEEEEE");
    trEdit();//td的点击事件封装成一个函数
});

/*下面兩段开始添加删除行**/
$(document).ready(function () {
    $("#addBtn").bind("click", function () {
        $("<tr><td></td><td></td><td class='del-col'><a href='javascript:void(0);' class='delBtn'>删除</a></td></tr>").insertBefore(".append-row");
        trEdit();
        delTr();
        $("tbody tr:odd").css("background-color", "#EEEEEE");
    });
    delTr();
});

//删除
function delTr() {
    $(".delBtn").click(function () {
        var arrayContent = [];
        $(this).closest('tr').find('td').each(function () {
            arrayContent.push($(this).not(".operate").text());
        })
        var userName = document.getElementById("userFlag").value;
        if (userName != null && userName != ""){
            jQuery.ajax({
                url:"deleteLabel",
                type:"POST",
                data:{"userName":userName, "label": arrayContent[0], "weight": arrayContent[1]},
                dataType:"json",
                success:function (res) {
                    if (res.result === "true"){
                        window.location.reload();
                    } else {
                        window.location.reload();
                    }
                },
                error:function (res) {
                    alert("删除失败")
                }
            });
        }else {
            alert("删除失败")
        }
    });
}

/*
function even(){
    $("tbody tr:even").css("background-color","#ECE9D8");
}*/


//我们需要找到所有的单元格
function trEdit() {
    var numTd = $("tbody td").not(".del-col");
    //给这些单元格注册鼠标点击的事件
    numTd.click(function () {
        //找到当前鼠标点击的td,this对应的就是响应了click的那个td
        var tdObj = $(this);
        var trObj = $(this).closest('tr');
        // var arrayContent = [];
        // trObj.find('td').each(function () {
        //     arrayContent.push($(this).not(".operate").text());
        // })
        // alert(arrayContent + "未修改");
        if (tdObj.children("input").length > 0) {
            //当前td中input，不执行click处理
            return false;
        }
        var text = tdObj.html();
        //清空td中的内容
        tdObj.html("");
        //创建一个文本框
        //去掉文本框的边框
        //设置文本框中的文字字体大小是12px
        //使文本框的宽度和td的宽度相同
        //设置文本框的背景色
        //需要将当前td中的内容放到文本框中
        //将文本框插入到td中
        var inputObj = $("<input type='text'>").css({
            "border": "0",
            "width": "100%",
            "height": "40px",
            "font-size": "16px",
            "text-align": "center"
        })
            .width(tdObj.width())
            .height(tdObj.height())
            .css("background-color", tdObj.css("background-color"))
            .val(text).appendTo(tdObj);
        //是文本框插入之后就被选中
        inputObj.trigger("focus").trigger("select");
        inputObj.click(function () {
            return false;
        });
        //处理文本框上回车和esc按键的操作
        inputObj.keyup(function (event) {
            //获取当前按下键盘的键值
            var keycode = event.which;
            //处理回车的情况
            if (keycode == 13) {
                //获取当当前文本框中的内容
                var inputtext = $(this).val();
                //将td的内容修改成文本框中的内容
                tdObj.html(inputtext);
                // $(this).closest('tr').find('td').each(function () {
                //     arrayContent.push($(this).not(".operate").text());
                // })
                // 获取修改后内容
                var alterArray = [];
                trObj.find('td').each(function () {
                    alterArray.push($(this).not(".operate").text());
                });
                if (alterArray[0] !== "" && alterArray[1] !== ""){
                    var reg = new RegExp("^[0-9]*$");
                    if(!reg.test(alterArray[1])){
                        alert("请输入数字！");
                    }else {
                        // alert(alterArray + "已修改");
                        var userName = document.getElementById("userFlag").value;
                        if (userName != null && userName != ""){
                            jQuery.ajax({
                                url:"updateLabel",
                                type:"POST",
                                data:{"userName":userName, "label": alterArray[0], "weight": alterArray[1]},
                                dataType:"json",
                                success:function (res) {
                                    if (res.result === "true"){
                                        alert("修改成功")
                                        window.location.reload();
                                    } else {
                                        window.location.reload();
                                    }
                                },
                                error:function (res) {
                                    alert("修改失败")
                                }
                            });
                        }else {
                            alert("修改失败")
                        }
                    }
                }else if (alterArray[0] === "" && alterArray[1] !== "") {
                    alert("每个标签填写完成后，记得先按Enter键确定！")
                }else if (alterArray[0] !== "" && alterArray[1] === "") {

                }
                else {
                    // alert(alterArray + "请补全");
                    alert("每个标签填写完成后，记得先按Enter键确定！")
                }
            }
            //处理esc的情况
            if (keycode == 27) {
                //将td中的内容还原成text
                tdObj.html(text);
            }
        });
    });
}


