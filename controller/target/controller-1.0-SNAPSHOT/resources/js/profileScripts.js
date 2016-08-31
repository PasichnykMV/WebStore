$(function(){
    $('#edit-profile-form').submit(function(e){
        e.preventDefault();
        var m_data=$(this).serialize() +"&edit=true";
        $.ajax({
            type: 'post',
            url: '/profile',
            data: m_data,
            success: function(){
                alert('Updated!');
            }
        });
    });
});
