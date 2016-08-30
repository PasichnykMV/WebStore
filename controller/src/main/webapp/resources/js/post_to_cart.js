$('#post_query').click(function(e){
        $.ajax({
            type: 'post',
            url: '/cart',
            data: d,
            dataType: "json",
            success: function(){
                alert("Updated!");
            }
        });
});