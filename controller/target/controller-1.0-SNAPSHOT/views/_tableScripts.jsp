<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<body>

<script src="<c:url value="/resources/js/jquery.dataTables.min.js"/>"></script>
<script src="<c:url value="/resources/js/dataTables.bootstrap.min.js"/>"></script>

<script>
    var oTable_datatable;
    var oTable_datatable_params;

    //            $(document).ready(function () {
    $(function () {
        oTable_datatable = $('#datatable');
        oTable_datatable_params = {

            "bPaginate": true,

            "bInfo": false,
            "aoColumns": [{
                "mData": "Foto"
            }, {
                "mData": "Title"
            },{
                "mData": "Description"
            }, {
                "mData": "City"
            }, {
                "mData": "Category"
            }, {
                "mData": "Price"
            }
            ],
            "aLengthMenu": [4, 8, 24, 32],

            "aoColumnDefs": [
                { "bSortable": false, "aTargets": [ 0,2 ] }
            ],

            "aaSorting": [
                [
                    4,
                    "asc"
                ]
            ]
        };

        oTable_datatable.dataTable(oTable_datatable_params);
    });


</script>
</body>
