$("#products_productlist").ready(function () {

    $.ajax("http://localhost:8080/sellnbye/api/product", {
        contentType: 'application/json',
        type: 'GET'
    }).done(function (response) {
        var newItem = "";
        $.each(response, function (index, value) {

            newItem += `<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12">
            <div class="personal-info-wrap">
                <div class="widget-head-info-box">
                    <div class="persoanl-widget-hd">
                    <h2 class="username_header_id" >${value.productName}</h2>
                    <h2 class="proId" >${value.productId}</h2>
                        <p>${value.creator}</p>
                    </div>
                    <img src="${value.productImage}" class=" m-b-md center" alt="profile"  width="500" height="500">
                    <input type="hidden" id="proId" name="proId" value="${value.productId}">
                    <div class="social-widget-result">
                        <span>${value.productPrice}</span> <br>
                        <span><button type="button" id="user_editUser_btn" class="btn btn-primary">
                        EDIT
                    </button></span> |
                        <span> <button type="button" id="product_delete_btn" class="btn btn-danger">
                        DELETE
                    </button></span>
                       
                      
                    </div>
                </div>
                
            </div>
        </div>`;
        });

        $("#products_productlist").append(newItem);
    });
});
$('body').on('click', '#product_delete_btn', function (event) {
    var proId = $(event.target).parent().parent().parent().parent().find('.proId').html();

       
    $.ajax(`http://localhost:8080/sellnbye/api/product/${proId}`, {
        
        contentType: 'application/json',
        type: 'DELETE'
    }).done(function (response) {
        location.reload();
        if (response) {
            alert("Successfully Deleted");
        } else {
            alert("Delete Failed");
        }
    });
});