// $(document).ready(function () {
const userAjaxUrl = "ajax/admin/users/";
$(function () {
    makeEditable({
            ajaxUrl: userAjaxUrl,
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "name"
                    },
                    {
                        "data": "email"
                    },
                    {
                        "data": "roles"
                    },
                    {
                        "data": "enabled"
                    },
                    {
                        "data": "registered"
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
                        "asc"
                    ]
                ]
            })
        }
    );
});


function enable(checkbox, id) {
    const enabled = checkbox.is(":checked");
    $.ajax({
        url: userAjaxUrl+id,
        type: "POST",
        data: "enabled="+enabled
    }).done(function () {
        successNoty(enabled ? "common.enabled" : "common.disabled");
        updateTable()
    }).fail(function () {
        $(checkbox).prop("checked", !enabled);
    });
}


