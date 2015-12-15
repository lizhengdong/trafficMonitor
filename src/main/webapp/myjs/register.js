function usernamesignupBlur() {
    //检测该用户名是否已经注册
    var aj = $.ajax({
        url: 'judgeUserName',
        data: {
            username: $('#usernamesignup').val()
        },
        type: 'post',
        cache: false,
        dataType: 'json',
        success: function (data) {
            console.log("data info:" + data);
            var result;
            if (typeof data === 'object') {
                result = data;
            }
            else {
                result = eval("(" + data + ")");
            }
            if (result.errorCode == "0") {
                // 成功
                console.log("该用户名可以注册");
            } else {
                console.log("该用户名已被注册");
                $('#usernamesignup').val("");
                $('#usernamesignup').placeholderText(result.errorMessage);
            }
        },
        error: function () {
            // 异常提示
        }
    });
}
function emailsignupBlur() {
    //检测该邮箱是否已经注册
    var aj = $.ajax({
        url: 'judgeMailbox',
        data: {
            mailbox:$('#emailsignup').val()
        },
        type: 'post',
        cache: false,
        dataType: 'json',
        success: function (data) {
            console.log("data info:" + data);
            var result;
            if (typeof data === 'object') {
                result = data;
            }
            else {
                result = eval("(" + data + ")");
            }
            if (result.errorCode == "0") {
                // 成功

            } else {
                $('#emailsignup').val("");
                $('#emailsignup').placeholderText(result.errorMessage);
            }
        },
        error: function () {
            // 异常提示
        }
    });
}