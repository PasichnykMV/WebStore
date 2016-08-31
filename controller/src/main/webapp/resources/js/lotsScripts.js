$('#title').change( function(){
    $('#new-title').val($(this).val());
})

$('#price').change( function(){
    var value = $(this).val();
    $('#new-price').val(value);
})

$('#category').change( function(){
    $('#new-category').val($(this).val());
})

$('#city').change( function(){
    $('#new-city').val($(this).val());
})

$('#description').change( function(){
    $('#new-description').val($(this).val());
})

$(function(){
    $('#lot-form').submit(function(e){

        e.preventDefault();

        var m_method=$(this).attr('method');

        var m_action=$(this).attr('action');

        var m_data=$(this).serialize() +"&type=create";
        $.ajax({
            type: m_method,
            url: m_action,
            data: m_data,
            success: function(){
                $('#lot-form').replaceWith( $('#update-form').show() );
            }
        });
    });
});

$(function(){
    $('#update-form').submit(function(e){

        e.preventDefault();

        var m_method=$(this).attr('method');

        var m_action=$(this).attr('action');

        var m_data=$(this).serialize() +"&type=create&edit=true";
        $.ajax({
            type: m_method,
            url: m_action,
            data: m_data,
            success: function(){
                alert('Updated!');
            }
        });
    });
});

$(document).on('click', '#close-preview', function(){
    $('.image-preview').popover('hide');
    // Hover befor close the preview
    $('.image-preview').hover(
        function () {
            $('.image-preview').popover('show');
        },
        function () {
            $('.image-preview').popover('hide');
        }
    );
});

$(function() {
    // Create the close button
    var closebtn = $('<button/>', {
        type:"button",
        text: 'x',
        id: 'close-preview',
        style: 'font-size: initial;',
    });
    closebtn.attr("class","close pull-right");
    // Set the popover default content
    $('.image-preview').popover({
        trigger:'manual',
        html:true,
        title: "<strong>Preview</strong>"+$(closebtn)[0].outerHTML,
        content: "There's no image",
        placement:'bottom'
    });
    // Clear event
    $('.image-preview-clear').click(function(){
        $('.image-preview').attr("data-content","").popover('hide');
        $('.image-preview-filename').val("");
        $('.image-preview-clear').hide();
        $('.image-preview-input input:file').val("");
        $(".image-preview-input-title").text("Browse");
    });
    // Create the preview image
    $(".image-preview-input input:file").change(function (){
        var img = $('<img/>', {
            id: 'dynamic',
            width:250,
            height:200
        });
        var file = this.files[0];
        var reader = new FileReader();
        // Set preview image into the popover data-content
        reader.onload = function (e) {
            $(".image-preview-input-title").text("Change");
            $(".image-preview-clear").show();
            $(".image-preview-filename").val(file.name);
            img.attr('src', e.target.result);
            $(".image-preview").attr("data-content",$(img)[0].outerHTML).popover("show");
        }
        reader.readAsDataURL(file);
    });
});

$(function(){
    $('#file-form').submit(function(e){

        e.preventDefault();
        var m_data= new FormData(this);
        $.ajax({
            url: '/lot',
            type: 'POST',
            data: m_data,
            contentType: false,
            processData: false,
            success: function(){
                alert("Images successfully uploaded!")
            }
        });
    });
});

$(function(){
    $('#cover-form').submit(function(e){

        e.preventDefault();
        var m_data= new FormData(this);
        $.ajax({
            url: '/lot',
            type: 'POST',
            data: m_data,
            contentType: false,
            processData: false,
            success: function(){
                alert("Cover image successfully uploaded!")
            }
        });
    });
});