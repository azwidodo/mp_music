function refresh() {
    $.ajax({
        url: "/all",
        type: "GET",
        dataType: "html",
        // result below is the return value of the all function at HomeController
        success: function (result) {
            $("#all-list").html(result)
        }
    })

}

$(document).ready(function () {
    refresh();

    // delete
    $(".delete-form").click(function () {
        console.log("delet successful")
    })

    // pop up add
    $("#new-song-btn").click(function () {
        $.ajax({
            url: "/insert",
            type: "GET",
            dataType: "html",
            success: function (result) {
                $('#add-mahasiswa').modal('show');
                $('#add-mahasiswa').find('.modal-body').html(result);
            }
        })
    });
})