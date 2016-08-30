<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@page session="true" %>
<%@page isELIgnored="false" %>

<style>
    .image-preview-input {
        position: relative;
        overflow: hidden;
        margin: 0px;
        color: #333;
        background-color: #fff;
        border-color: #ccc;
    }
    .image-preview-input input[type=file] {
        position: absolute;
        top: 0;
        right: 0;
        margin: 0;
        padding: 0;
        font-size: 20px;
        cursor: pointer;
        opacity: 0;
        filter: alpha(opacity=0);
    }
    .image-preview-input-title {
        margin-left:2px;
    }
</style>
<body>
<div class="col-lg-4">
    <br>
    <label class="lot_label_left">Upload lots cover-image:</label>
    <form id="cover-form" action="javascript:;" method="post" enctype="multipart/form-data">
        <br>
        <input type="hidden" name="type" value="cover">

    <div class="input-group image-preview">
        <input type="text" class="form-control image-preview-filename" disabled="disabled"> <!-- don't give a name === doesn't send on POST/GET -->
                <span class="input-group-btn">
                    <!-- image-preview-clear button -->
                    <button type="button" class="btn btn-default image-preview-clear" style="display:none;">
                        <span class="glyphicon glyphicon-remove"></span> Clear
                    </button>
                    <!-- image-preview-input -->
                    <div class="btn btn-default image-preview-input">
                        <span class="glyphicon glyphicon-folder-open"></span>
                        <span class="image-preview-input-title">Browse</span>
                        <input type="file" accept="image/png, image/jpeg, image/gif" name="input-file-preview"/> <!-- rename it -->
                    </div>
                </span>
    </div><!-- /input-group image-preview [TO HERE]-->
        <br>
        <input type="submit">
    </form>

    <script>
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
    </script>

    <br>
    <label class="lot_label_left">Upload lots images:</label>
    <form id="file-form" action="javascript:;" method="post" enctype="multipart/form-data">
        <br>
        <input type="hidden" name="type" value="images">
        <input type="file" accept="image/png, image/jpeg, image/gif" name="file" multiple/>
        <br>
        <input type="submit">
    </form>

    <script>
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
    </script>

    <script>
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
    </script>

</div>
<div class="col-lg-4">
    <br>
    <form id="lot-form" action="lot" method="post">
        <div class="input-group">
            <span class="input-group-addon"><span class="glyphicon glyphicon-tags"></span></span>
            <input type="text" id="title" class="form-control" name="title"
                   placeholder="Lot title" required>
        </div>
        <br>
        <div class="input-group">
            <input type="text" id="price" class="form-control" name="price"
                   placeholder="Price" required>
            <span class="input-group-addon">$</span>
        </div>
        <br>
        <div class="form-group">
            <label class="lot_label_left">Category</label>
            <select class="form-control" id="category" name="categoryId" required>
                <c:forEach var="cat" items="${categories}" varStatus="Status">
                    <option name="categoryId" value="${cat.id}">
                            <c:out value="${cat.title}"/>
                    </option>
                </c:forEach>
            </select>
        </div>
        <br>
        <div class="form-group">
            <label class="lot_label_left">Location</label>
            <select class="form-control" id="city" name="cityId" required>
                <c:forEach var="city" items="${cities}" varStatus="Status">
                    <option name="cityId" value="${city.id}">
                        <c:out value="${city.name}"/>
                    </option>
                </c:forEach>
            </select>
        </div>
        <br>
        <div class="form-group">
            <textarea class="form-control" id="description" rows="3" name="description" placeholder="Lot description"
                   required></textarea>
        </div>
        <br>
        <button class="btn btn-default" id="submit_button" style="margin-left: 40%;"
                type="submit" >Create</button>
    </form>

    <form id="update-form" action="lot" method="post" style="display: none;">
        <div class="input-group">
            <span class="input-group-addon"><span class="glyphicon glyphicon-tags"></span></span>
            <input type="text" id="new-title" class="form-control" name="title" placeholder="Lot title" required>
        </div>
        <br>
        <div class="input-group">
            <input type="text" id="new-price" class="form-control" name="price" placeholder="Price" required>
            <span class="input-group-addon">$</span>
        </div>
        <br>
        <div class="form-group">
            <label class="lot_label_left">Category</label>
            <select class="form-control" id="new-category" name="categoryId" required>
                <c:forEach var="cat" items="${categories}" varStatus="Status">
                    <option name="categoryId" value="${cat.id}">
                        <c:out value="${cat.title}"/>
                    </option>
                </c:forEach>
            </select>
        </div>
        <br>
        <div class="form-group">
            <label class="lot_label_left">Location</label>
            <select class="form-control" id="new-city" name="cityId" required>
                <c:forEach var="city" items="${cities}" varStatus="Status">
                    <option name="cityId" value="${city.id}">
                        <c:out value="${city.name}"/>
                    </option>
                </c:forEach>
            </select>
        </div>
        <br>
        <div class="form-group">
            <textarea class="form-control" id="new-description" rows="3" name="description" placeholder="Lot description"
                      required></textarea>
        </div>
        <br>
        <button class="btn btn-default" id="update_button" style="margin-left: 40%;"
                type="submit" >Edit</button>
    </form>

    <script>
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

    </script>
</div>

<div class="col-lg-4"></div>

<script type="text/javascript" src="webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</body>
