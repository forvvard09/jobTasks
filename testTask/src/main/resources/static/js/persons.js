$(document).ready(function () {

    connect();

    var personRequiredField = $("#personForm");
    var idField = $("#idForm");

    jQuery.validator.addMethod("check", function (value, element, param) {
        return value.match(new RegExp("^" + param + "$"));
    });


    idField.validate({
        rules: {
            personId: {
                required: true,
                check: "[0-9]+"
            }
        },
        messages: {
            personId: "   Заполните обязательное поле id."
        }
    });

    personRequiredField.validate({
        rules: {
            lastName: {check: "[а-яА-Я]+"},
            firstName: {check: "[а-яА-Я]+"},
            middleName: {check: "[А-яА-Я]+"},
            birthDate: {required: true}
        },
        messages: {
            lastName: "   Обязательное поле. Можно использовать только русские буквы.",
            firstName: "   Обязательное поле. Можно использовать только русские буквы.",
            middleName: "   Обязательное поле. Можно использовать только русские буквы.",
            birthDate: "   Обязательное поле. Формат даты: чч.мм.гггг."
        }
    });

    //--add Person
    $("#btnAddNewPerson").click(function (event) {
        if (personRequiredField.valid()) {
            event.preventDefault();
            ajaxAddNewPerson();
            sendMessage();
        }

    });

    //--update person
    $("#btnUpdatePerson").click(function (event) {
        if (personRequiredField.valid() && idField.valid()) {
            event.preventDefault();
            ajaxUpdatePerson();
            sendMessage();
        }

    });

    //--getAllPersons
    $("#btnGetAllPersons").click(function (event) {
        event.preventDefault();
        ajaxGetAllPersons();
        sendMessage();
    });

    //--working
    $("#btnWorking").click(function (event) {
        event.preventDefault();
        ajaxWorking();
        sendMessage();
    });

    //--getPersonById
    $("#btnGetPersonById").click(function (event) {
        if (idField.valid()) {
            event.preventDefault();
            ajaxGetPersonById();
            sendMessage();
        }
    });


    //--remove Person
    $("#btnRemovePerson").click(function (event) {
        if (idField.valid()) {
            event.preventDefault();
            ajaxRemovePerson();
            sendMessage();
        }
    });

//---------

    function ajaxAddNewPerson() {
        var formData = {
            "id": $("#personId").val(),
            "lastName": $("#lastName").val(),
            "firstName": $("#firstName").val(),
            "middleName": $("#middleName").val(),
            "birthDate": $("#birthDate").val()
        };

        $.ajax({
            type: 'POST',
            url: '/persons',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(formData),
            success: function () {
                alert("Пользователь успешно сохранен в безе данных.");
                clearInputData();
            },
            error: function (jqXHR) {
                alert("Status: " + jqXHR.status + ". Этот id уже используется в базе данных.");
            }
        });
    }

    function ajaxGetPersonById() {
        var id = $("#personId").val();
        $.ajax({
            type: 'GET',
            url: '/persons/' + id,
            dataType: "json",
            success: function (data) {
                $('#tableOnePerson tbody').empty();
                if (data.comment == null) {
                    data.comment = "";
                }
                if (data.updateDate == null) {
                    data.updateDate = "";
                }
                var recordOnePerson = '<tr>' +
                    '<td>' + data.id + '</td>' +
                    '<td>' + data.lastName + '</td>' +
                    '<td>' + data.firstName + '</td>' +
                    '<td>' + data.middleName + '</td>' +
                    '<td>' + data.birthDate + '</td>' +
                    '<td>' + data.comment + '</td>' +
                    '<td>' + data.updateDate + '</td>' +
                    '</tr>';
                $('#tableOnePerson tbody').append(recordOnePerson);
                clearInputData();
            },
            error: function (jqXHR) {
                alert("Status: " + jqXHR.status + ". Ошибка, пользователь с таким id не найден.");
            }
        });

    }

    function ajaxGetAllPersons() {

        $.ajax({
            type: 'GET',
            url: '/persons/',
            success: function (data) {
                $('#tableListAllUsers tbody').empty();
                $.each(data, function (i, person) {

                    if (person.comment == null) {
                        person.comment = "";
                    }
                    var recordOnePerson = '<tr>' +
                        '<td><input type="checkbox"' + 'id="' + person.id + '"></td>' +
                        '<td>' + person.id + '</td>' +
                        '<td>' + person.lastName + '</td>' +
                        '<td>' + person.firstName + '</td>' +
                        '<td>' + person.middleName + '</td>' +
                        '<td>' + person.birthDate + '</td>' +
                        '<td>' + person.comment + '</td>' +
                        '</tr>';
                    $('#tableListAllUsers tbody').append(recordOnePerson);
                });
            },
            error: function () {
                alert("Список пользователей пуст.");
            }
        });
    }

    function ajaxUpdatePerson() {
        var id = $("#personId").val();
        var formData = {
            "id": id,
            "lastName": $("#lastName").val(),
            "firstName": $("#firstName").val(),
            "middleName": $("#middleName").val(),
            "birthDate": $("#birthDate").val()
        };

        $.ajax({
            type: 'PUT',
            url: '/persons/' + id,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(formData),
            success: function () {
                alert("Пользователь успешно обновлен.");
                clearInputData();
            },
            error: function (jqXHR) {
                alert("Status: " + jqXHR.status + ". Ошибка. Пользвоатель с id = " + id + " не найден.");
            }
        });
    }

    function ajaxRemovePerson() {
        var id = $("#personId").val();
        $.ajax({
            type: 'DELETE',
            url: '/persons/' + id,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function () {
                alert("Пользователь с id = " + id + " успешно удален.");
                clearInputData();
            },
            error: function () {
                alert("Пользователь с данным id = " + id + " не найден.");
            }
        });
    }

    function ajaxWorking() {
        var checkedPersons = []
        // language=JQuery-CSS
        $('input[type=checkbox]:checked').each(function () {
            checkedPersons.push($(this).attr('id'));
        });
        if (checkedPersons.length === 0) {
            alert("Не выбрано ни одного пользователя для обработки.")
            return;
        }
        $.ajax({
            type: 'PUT',
            url: '/persons/working',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(checkedPersons),
            success: function () {
                alert("Операция обработки над выполненными записями успешно выплонена.");
                ajaxGetAllPersons();
            }
        });
    }
});

function clearInputData() {
    var tableForm = $("#personForm");
    var idForm = $("#idForm");
    idForm.trigger("reset");
    tableForm.trigger("reset");
}