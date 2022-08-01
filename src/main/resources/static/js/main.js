function refresh() {
    $.ajax({
        url: "/",
        type: "GET",
        dataType: "html",
        // result below is the return value of the all function at HomeController
        success: function (result) {
        }
    })
}

function deleteSong() {
    alert("Song will be deleted")
}

var id = null

function updateSong(id2) {
    id = id2
    console.log(id)
}

$(document).ready(function () {

    // submit insert
    $("#insert-song").on("submit", "#insert-song-form", function () {
        if ($("#insert-song .modal-title").text() == "Add new song") {
            $.ajax({
                url: "/admin/insertSubmit",
                type: "POST",
                data: $(this).serialize(),
                success: function (result) {
                    alert("New song added")
                    $("#modal").modal("hide");
                },
                error: function (result) {
                    alert("Error adding new song")
                }
            })
        } else {
            $.ajax({
                url: "/user/updateSubmit?id=" + id,
                type: "POST",
                data: $(this).serialize(),
                success: function (result) {
                    alert("Song updated")
                    $("#modal").modal("hide");
                },
                error: function (result) {
                    alert("Error updating new song")
                }
            })

            id = null
        }

        refresh()

        return false
    })

    // update modal
    $("#new-song-btn, .table #update-btn").on("click", function (event) {

        event.preventDefault()

        var href = $(this).attr("href")
        var text = $(this).text();

        if (text == "Edit") {
            $.get(href, function (song) {
                $("#insert-song #name").val(song.songName)
                $("#insert-song #artist").val(song.artist)
                $("#insert-song #year").val(song.year)
                $("#insert-song #genre").val(song.genre)

                const platforms = song.platforms
                if (platforms.includes("Spotify")) {
                    $("#insert-song #spotify").prop("checked", true)
                } else { $("#insert-song #spotify").prop("checked", false) }
                if (platforms.includes("Youtube Music")) {
                    $("#insert-song #youtube").prop("checked", true)
                } else { $("#insert-song #youtube").prop("checked", false) }
                if (platforms.includes("Apple Music")) {
                    $("#insert-song #apple").prop("checked", true)
                } else { $("#insert-song #apple").prop("checked", false) }
                if (platforms.includes("Deezer")) {
                    $("#insert-song #deezer").prop("checked", true)
                } else { $("#insert-song #deezer").prop("checked", false) }
                if (platforms.includes("JOOX")) {
                    $("#insert-song #joox").prop("checked", true)
                } else { $("#insert-song #joox").prop("checked", false) }
            })

            $("#insert-song .modal-title").text("Edit song")
        } else {
            $("#insert-song #name").val("")
            $("#insert-song #artist").val("")
            $("#insert-song #year").val("")
            $("#insert-song #genre").val("")

            $("#insert-song #spotify").prop("checked", false)
            $("#insert-song #youtube").prop("checked", false)
            $("#insert-song #apple").prop("checked", false)
            $("#insert-song #deezer").prop("checked", false)
            $("#insert-song #joox").prop("checked", false)

            $("#insert-song .modal-title").text("Add new song")
        }

        $("#modal").modal("show");
    })
})