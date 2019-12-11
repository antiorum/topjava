var mealsAjaxUrl = "ajax/profile/meals/";

function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: mealsAjaxUrl + "filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(mealsAjaxUrl, updateTableByData);
}

$(function () {
    makeEditable({
        ajaxUrl: mealsAjaxUrl,
        datatableApi: $("#datatable").DataTable({
            "ajax": {
                "url": mealsAjaxUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                    "render": function (date, type, row) {
                        if (type === "display") {
                            return formatDateTimeFromISO(date)
                        }
                        return date;
                    }
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "",
                    "orderable": false,
                    "render": renderEditBtn
                },
                {
                    "defaultContent": "",
                    "orderable": false,
                    "render": renderDeleteBtn
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
            "createdRow": function (row, data, dataIndex) {
                if (data.excess) {
                    $(row).attr("data-mealExcess", true);
                } else {
                    $(row).attr("data-mealExcess", false);
                }
            }
        }),
        updateTable: updateFilteredTable
    });
});


$('#dateTime').datetimepicker({
    format: 'Y-m-d H:i'
});

$('#startDate').datetimepicker({
    timepicker: false,
    format: 'Y-m-d'
});


$('#endDate').datetimepicker({
    timepicker: false,
    format: 'Y-m-d',
});
$('#startTime').datetimepicker({
    datepicker: false,
    format: 'H:i'
});

$('#endTime').datetimepicker({
    datepicker: false,
    format: 'H:i',
});

const $startTime = document.querySelector('#startTime');
let startTime = '00:00';
$startTime.addEventListener('input', function () {
    startTime = $startTime.value === '' ? '00:00' : $startTime.value;
    $('#endTime').datetimepicker({
        minTime: startTime
    })
});

const $endTime = document.querySelector('#endTime');
let endTime = '24:00';
$endTime.addEventListener('input', function () {
    endTime = $endTime.value === '' ? '24:00' : $endTime.value;
    $('#startTime').datetimepicker({
        maxTime: endTime
    })
});

const $startDate = document.querySelector('#startDate');
let startDate = '1970-01-01';
$startDate.addEventListener('input', function () {
    startDate = $startDate.value === '' ? '1970-01-01' : $startDate.value;
    $('#endDate').datetimepicker({
        minDate: startDate
    });
});

