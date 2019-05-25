$("#orders_orderlist").ready(function () {

    $.ajax("http://localhost:8080/sellnbye/api/order", {
        contentType: 'application/json',
        type: 'GET'
    }).done(function (response) {
        var newItem = "";
        $.each(response, function (index, value) {

            newItem += `<hr class="soften">
            <div class="row-fluid">
                <div class="span6">
                    
                    <h5>${value.companyName}</h5>
                    <p>
                       Supplier: ${value.supplierName}
                    </p>
                    </br>
                    <p>
                        Needed Product: ${value.product}
                    </p>
                    </br>
                    <p>
                        Needed Quantity: ${value.qty}
                    </p>
                </div>
                <div class="span4 alignR">
                    
                </div>
            </div>`;
        });

        $("#orders_orderlist").append(newItem);
    });
});
