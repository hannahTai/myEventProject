// Datepicker
$("#evetit_startdate").datepicker({
    defaultDate: "+1w",
    changeMonth: true,
    changeYear: true,
    dateFormat: "yy-mm-dd",
    minDate: 0,
    onClose: function(selectedDate) {
        $("#evetit_enddate").datepicker("option", "minDate", selectedDate);
    }
});

$("#evetit_enddate").datepicker({
    defaultDate: "+1w",
    changeMonth: true,
    changeYear: true,
    dateFormat: "yy-mm-dd",
    onClose: function(selectedDate) {
        $("#evetit_startdate").datepicker("option", "maxDate", selectedDate);
    }
});

// $("#launchdate").datepicker({
//     defaultDate: "+1w",
//     changeMonth: true,
//     changeYear: true,
//     dateFormat: "yy-mm-dd",
//     timepicker: false,
//     minDate: 0,
//     onClose: function(selectedDate) {
//         $("#offdate").datepicker("option", "minDate", selectedDate);
//     }
// });

// $("#offdate").datepicker({
//     defaultDate: "+1w",
//     changeMonth: true,
//     changeYear: true,
//     dateFormat: "yy-mm-dd",
//     onClose: function(selectedDate) {
//         $("#launchdate").datepicker("option", "maxDate", selectedDate);
//     }
// });

function imagesPreview(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function(e) {
            $("#evetit_poster_preview").attr("src", e.target.result);
        }
        reader.readAsDataURL(input.files[0]);
    }
}