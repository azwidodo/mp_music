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
    $("#delete-btn").submit("#delete-btn-form", function () {
        $.ajax({
            url: "/",
            type: "GET",
            success: function (result) {
                alert("Delete successful")
                refresh()
            }
        })
        return false
    })

    // pop up add
    $("#new-song-btn").click(function () {
        $.ajax({
            url: "/insertModal",
            type: "GET",
            dataType: "html",
            success: function (result) {
                $("#insert-song").modal("show");
                $("#insert-song").find(".modal-body").html(result);
            }
        })
    });

    // submit insert
    $("#insert-song").submit("#insert-song-form", function () {
        $.ajax({
            url: "/insertSubmit",
            type: "GET",
            data: $(this).serialize(),
            success: function (result) {
                alert("New song added")
                $("#insert-song").modal("hide");
                refresh();
            }
        })
        return false
    })
})