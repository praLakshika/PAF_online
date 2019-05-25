
  $("#users_userlist").ready(function () {

    $.ajax("http://localhost:8080/sellnbye/api/user", {
        contentType: 'application/json',
        type: 'GET'
    }).done(function (response) {
        var newItem = "";
        $.each(response, function (index, value) {
            newItem +=   `<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12">
            <div class="personal-info-wrap">
                <div class="widget-head-info-box">
                    <div class="persoanl-widget-hd">
                        <h2 class="username_header_id" >${value.username}</h2>
                        <p>${value.email}</p>
                    </div>
                    <img src="${value.profilePicture}" class="img-circle circle-border m-b-md center" alt="profile"  width="150" height="150">
                    <div class="social-widget-result">
                        <span>${value.userType}</span> <br>
                        <span>
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">
                     Edit
                    </button>
                    </span> |
                        <span> <button type="button" id="user_deleteUser_btn" class="btn btn-danger">
                        DELETE
                    </button></span>
                    <!-- Button trigger modal -->
                    
                    
                    <!-- Modal -->
                    <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                      <div class="modal-dialog" role="document">
                        <div class="modal-content">
                          <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                              <span aria-hidden="true">&times;</span>
                            </button>
                          </div>
                          <div class="modal-body">
                          <form id="edituser_form" class="form-horizontal">
                          <div class="row">
                          <h3  style="color:red;">User name</h3>
                          <h2 class="username_header_id" style="color:DodgerBlue;">${value.username}</h2>
                              
                              <div class="form-group col-lg-12">
                                  <label style="color:red;" >Contact Number</label>
                                  <input  class="form-control" type="text" id="registration_inputContactnumber" placeholder="Contact Number" required="" value="${value.contactNo}">
                              </div>
                              <div class="form-group col-lg-12">
                                  <label style="color:red;">Email Address</label>
                                  <input  class="form-control" type="Email" id="registration_inputEmail" placeholder="Email" required=""  value="${value.email}">
                              </div>
                              
                              
                          </div>
                          
                          </div>
                          <div class="modal-footer">
                            <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                            <input class="btn btn-primary loginbtn" type="submit" name="submitAccount" value="Update" class="exclusive shopBtn" value="Edit">
               
                          </div>
                      </form>
                        </div>
                      </div>
                    </div>
                      
                    </div>
                </div>
                
            </div>
        </div>
        1 <script >
$("#edituser_form").submit(function (event) {
    event.preventDefault();
    alert("Added successfully");
    var requestData = {
        username : $("#edituser_inputUsername").val(),
        isActive : $("#edituser_isActive").prop('checked'),
        contactNo : $("#edituser_inputContactnumber").val(),
        email : $("#edituser_inputEmail").val()
    };
    console.log(requestData);
    $.ajax("http://localhost:8080/sellnbye/api/user", {
                data: JSON.stringify(requestData),
                contentType: 'application/json',
                type: 'PUT'
            }).done(function (response) {
                if (response === true) {
                    location.reload();
                    alert("Edited successfully");
                }
                else {
                    alert("Editing user failed");
                }
            });
    $('#exampleModal').modal('toggle');
});</script>`;

           
        });

        $("#users_userlist").append(newItem);
    });
});

$("#login_form").submit(function (event) {
    event.preventDefault();
    data = {
        "password": SHA256($("#login_inputPassword").val()).toUpperCase(),
        "username": $("#login_inputUserName").val()
    }
    $.ajax("http://localhost:8080/sellnbye/api/user/login", {
        data: JSON.stringify(data),
        contentType: 'application/json',
        type: 'POST'
    }).done(function (response) {
        console.log(response);
    });
});

$('body').on('click', '#user_editUser_btn', function (event) {
    var username = $(event.target).parent().parent().parent().parent().find('.username_header_id').html();

    $.ajax(`http://localhost:8080/sellnbye/api/user/${username}`, {
        contentType: 'application/json',
        type: 'GET'
    }).done(function (response) {
        $("#edituser_inputUsername").val(response.username);
        $("#edituser_inputContactnumber").val(response.contactNo);
        $("#edituser_inputEmail").val(response.email);

        if (response.isActive) {
            $("#edituser_isActive").prop('checked', true);
        } else {
            $("#edituser_isActive").prop('checked', false);
        }
    });

    $('#exampleModal').modal('show');
});

$('body').on('click', '#user_deleteUser_btn', function (event) {
    var username = $(event.target).parent().parent().parent().parent().find('.username_header_id').html();

    $.ajax(`http://localhost:8080/sellnbye/api/user/${username}`, {
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

$("#edituser_form").submit(function (event) {
    event.preventDefault();
    alert("Added successfully");
    var requestData = {
        username : $("#edituser_inputUsername").val(),
        isActive : $("#edituser_isActive").prop('checked'),
        contactNo : $("#edituser_inputContactnumber").val(),
        email : $("#edituser_inputEmail").val()
    };
    console.log(requestData);

    $.ajax("http://localhost:8080/sellnbye/api/user", {
                data: JSON.stringify(requestData),
                contentType: 'application/json',
                type: 'PUT'
            }).done(function (response) {
                if (response === true) {
                    location.reload();
                    alert("Edited successfully");
                }
                else {
                    alert("Editing user failed");
                }
            });

    $('#exampleModal').modal('toggle');
});