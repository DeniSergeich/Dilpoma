<script>
$(document).ready(function(){
    $("#accordion").on('hidden.bs.collapse', toggleIcon);
    $("#accordion").on('shown.bs.collapse', toggleIcon);
});

function toggleIcon(e) {
    $(e.target)
        .prev('.card-header')
        .find("i.indicator")
        .toggleClass("fas fa-minus fas fa-plus");
}
</script>