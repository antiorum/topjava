$(function () {
    makeEditable({
            ajaxUrl: "ajax/meals/",
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "dateTime"
                    },
                    {
                        "data": "description"
                    },
                    {
                        "data": "calories"
                    },
                    {
                        "defaultContent": "Edit",
                        "orderable": false
                    },
                    {
                        "defaultContent": "Delete",
                        "orderable": false
                    }
                ],
                "order": [
                    [
                        0,
                        "desc"
                    ]
                ]
            })
        }
    );
});

jQuery('#dateTime').datetimepicker({
    format: 'Y-m-d H:i'
});

jQuery('#startDate').datetimepicker({
    timepicker:false,
    format:'Y-m-d'
});
jQuery('#endDate').datetimepicker({
    timepicker:false,
    format:'Y-m-d'
});
jQuery('#startTime').datetimepicker({
    datepicker:false,
    format:'H:i'
});
jQuery('#endTime').datetimepicker({
    datepicker:false,
    format:'H:i'
});

function filter() {
    form=$('#filterForm');
    $.ajax({
        type: "POST",
        url: 'ajax/meals/filter',
        data: form.serialize(),
        success: function(data){
            $("#datatable").DataTable().clear().rows.add(data).draw();
        }
    });
}

function resetForm() {
    $('#filterForm')[0].reset();
    updateTable();
}