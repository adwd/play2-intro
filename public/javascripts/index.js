$(function(){
  $("#sendbtn").click(function(){
    var jsondata = {
      'input': $("#input").val()
    };
    $.post("/ajax",
      jsondata,
      function(result){
        var res = "result:" + result.status + ", message:" + result.message;
        $("#message").text(res);
      },
      "json"
    );
  })
})
