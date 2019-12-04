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

$('#dateTime').datetimepicker({
    format: 'Y-m-d H:i'
});

$('#startDate').datetimepicker({
    timepicker:false,
    format:'Y-m-d'
});
$('#endDate').datetimepicker({
    timepicker:false,
    format:'Y-m-d'
});
$('#startTime').datetimepicker({
    datepicker:false,
    format:'H:i'
});
$('#endTime').datetimepicker({
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

function saveMeal() {
    form=$('#detailsForm');
    $.ajax({
        type: "POST",
        url: context.ajaxUrl,
        data: form.serialize()
    }).done(function () {
        $("#editRow").modal("hide");
        filter();
        successNoty("Saved");
    });
}

function resetForm() {
    $('#filterForm')[0].reset();
    updateTable();
}

function writeFormValuesAndAdd() {
    //sad, but true
    var startDate = $('#startDate').val();
    var endDate = $('#endDate').val();
    var startTime = $('#startTime').val();
    var endTime = $('#endTime').val();
    add();
    $('#startDate').val(startDate);
    $('#endDate').val(endDate);
    $('#startTime').val(startTime);
    $('#endTime').val(endTime);
}