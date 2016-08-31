$(document).ready( function() {

    var sum = 0;

    $('#mytable .price-td').each(function () {
        var price = parseFloat($(this).html());
        sum += price;
    })
    $('#total_price').html(sum);
});