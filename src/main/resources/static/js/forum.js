function post() {
    const questionId = $("#question_id").val();
    const commentContent = $("#comment_content").val();

    $.ajax({
        type: "post",
        url: "/comment",
        data: JSON.stringify({
                parentId: questionId,
                content: commentContent,
                type: 1
            }
        ),
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        success: function (response) {
            alert(response.message)
        }
    })
    ;

}