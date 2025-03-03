$(document).ready(function () {

    const csrfToken = $('meta[name="_csrf"]').attr('content');
    const csrfHeader = $("meta[name='_csrf_header']").attr("content");

    function searchMembers() {
        let memberKeyword = $("#memberSearchInput").val().trim();

        $.ajax({
            url: "/api/admin/member/search",
            type: "GET",
            data: { memberKeyword: memberKeyword },
            success: function (response) {
                if (response.status !== 200) {
                    alert("검색에 실패했습니다.");
                    return;
                }

                let members = response.data;
                let tableBody = $("#memberTableBody");
                tableBody.empty();

                if (members.length === 0) {
                    tableBody.append("<tr><td colspan='5' class='text-center'>검색 결과가 없습니다.</td></tr>");
                    return;
                }

                members.forEach(member => {
                    let row = `
                            <tr>
                                <td><input type="checkbox" class="memberCheckbox" value="${member.id}"></td>
                                <td>${member.name}</td>
                                <td>${member.email}</td>
                                <td></td>
                                <td>${member.status}</td>
                            </tr>`;
                    tableBody.append(row);
                });
            },
            error: function () {
                alert("검색 중 오류가 발생했습니다.");
            }
        });
    }

    function searchLessons() {
        let lessonKeyword = $("#lessonSearchInput").val().trim();

        $.ajax({
            url: "/api/admin/lesson/search",
            type: "GET",
            data: { lessonKeyword: lessonKeyword },
            success: function (response) {
                if (response.status !== 200) {
                    alert("검색에 실패했습니다.")
                }

                let lessons = response.data;
                let tableBody = $("#lessonTableBody");
                tableBody.empty();

                if (lessons.length === 0) {
                    tableBody.append("<tr><td colspan='3' class='text-center'>검색 결과가 없습니다.</td></tr>")
                    return;
                }

                lessons.forEach(lesson => {
                    let row = `
                            <tr>
                                <td><input type="checkbox" class="lessonCheckbox" value="${lesson.id}"></td>
                                <td>${lesson.title}</td>
                                <td>${lesson.masterName}</td>
                            </tr>`;
                    tableBody.append(row);
                });
            },
            error: function () {
                alert("검색 중 오류가 발생했습니다.")
            }
        });
    }

    function searchBoards() {
        let boardKeyword = $("#boardSearchInput").val().trim();

        $.ajax({
            url: "/api/admin/board/search",
            type: "GET",
            data: { boardKeyword: boardKeyword },
            success: function (response) {
                if (response.status !== 200) {
                    alert("검색에 실패했습니다.")
                }

                let boards = response.data;
                let tableBody = $("#boardTableBody");
                tableBody.empty();

                if (boards.length === 0) {
                    tableBody.append("<tr><td colspan='3' class='text-center'>검색 결과가 없습니다.</td></tr>")
                    return;
                }

                boards.forEach(board => {
                    let row = `
                            <tr>
                                <td><input type="checkbox" class="boardCheckbox" value="${board.id}"></td>
                                <td>${board.title}</td>
                                <td>${board.memberName}</td>
                            </tr>`;
                    tableBody.append(row);
                });
            },
            error: function () {
                alert("검색 중 오류가 발생했습니다.")
            }
        });
    }

    $("#memberSearchButton").click(function () {
        searchMembers();
    });
    $("#memberSearchInput").keypress(function (event) {
        if (event.which === 13) {
            searchMembers();
        }
    });

    $("#lessonSearchButton").click(function () {
        searchLessons();
    });
    $("#lessonSearchInput").keypress(function (event) {
        if (event.which === 13) {
            searchLessons();
        }
    })

    $("#boardSearchButton").click(function () {
        searchBoards();
    });
    $("#boardSearchInput").keypress(function (event) {
        if (event.which === 13) {
            searchBoards();
        }
    })

    $("#insertCategoryButton").click(function () {
        $('#insertCategoryModal').modal('show');
    })

    $("#applyTableBody").on("click","tr", function () {
        let applyId = $(this).find(".applyId").val();
        let applyMemberId = $(this).find(".applyMemberId").val();
        let applyName = $(this).find(".applyName").text();
        let applyEmail = $(this).find(".applyEmail").text();
        let applyFile = $(this).data("apply_file");

        $("#applyId").val(applyId)
        $("#applyMemberId").val(applyMemberId)
        $("#applyName").text(applyName)
        $("#applyEmail").text(applyEmail)

        $("#applyImgContainer").empty();

        if (applyFile) {
            let imageUrl = `/license/${applyFile}`;
            let img = $("<img>").attr("src", imageUrl).attr("alt", "자격증 이미지").css({
                "max-width": "100%",
                "max-height": "300px",
                "margin-bottom": "10px"
            });
            $("#applyImgContainer").append(img);
        }

        $("#applyDetailModal").modal("show");
    })

    $("#agreeApplyButton").click(function () {
        let applyMemberId = $("#applyMemberId").val();

        console.log(applyMemberId);

        $.ajax({
            url: "/api/admin/member/master",
            type: "POST",
            data: JSON.stringify({memberId: Number(applyMemberId)}),
            contentType: 'application/json; charset=utf-8',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (response) {
                if (response.status === 200) {
                    $("#applyDetailModal").modal("hide");
                    alert("강사 신청이 승인되었습니다.");
                    location.reload();
                } else {
                    alert("승인에 실패하였습니다.");
                    console.log(response.status);
                    console.log(response.data);
                }
            },
            error: function (xhr, status, error) {
                console.error("AJAX Error : ", status, error);
                console.error("Response : ", xhr.responseText);
                alert("추가 중 오류가 발생하였습니다.")
            }
        })
    })

    $("#insertSaveCategoryButton").click(function () {
        const categoryData = {
            name: $("#categoryName").val(),
            code: $("#categoryCode").val(),
        }

        if (categoryName.length === 0) {
            alert("카테고리명을 입력해주세요!");
            return
        }

        $.ajax({
            url: "/api/admin/category/insert",
            type: "POST",
            data: JSON.stringify(categoryData),
            contentType: 'application/json; charset=utf-8',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (response) {
                if (response.status === 200) {
                    $("#categoryModal").modal("hide");
                    alert("카테고리가 추가되었습니다!");
                    location.reload();
                } else {
                    alert("카테고리 추가에 실패했습니다.");
                    console.log(response.status);
                    console.log(response.data);
                }
            },
            error: function (xhr, status, error) {
                console.error("AJAX Error : ", status, error);
                console.error("Response : ", xhr.responseText);
                alert("추가중 오류가 발생하엿습니다.")
            }
        });
    });

    $("#updateCategoryButton").click(function () {
        let selectCategories = [];
        let selectCategoriesName = "";

        $(".categoryCheckbox:checked").each(function () {
            selectCategories.push(Number($(this).val()));
            selectCategoriesName = $(this).closest('tr').find('td:eq(1)').text();
        });

        if (selectCategories.length !== 1) {
            alert("한개의 카테고리만 골라주세요!");
            return;
        }
        $("#originalCategoryName").val(selectCategoriesName);
        $("#updateCategoryId").val(selectCategories[0]);

        $("#updateCategoryModal").modal('show');
    })

    $("#updateSaveCategoryButton").click(function () {
        const categoryId = $("#updateCategoryId").val();
        const categoryName = $("#updateCategoryName").val();
        const categoryCode = $("#updateCategoryCode").val();

        if (!categoryName || categoryName.trim() === "") {
            alert("카테고리명을 입력해주세요!");
            return;
        }

        console.log(categoryId, categoryName, categoryCode);

        const updateData = {
            id: Number(categoryId),
            name: categoryName,
            code: categoryCode
        };

        $.ajax({
            url: "/api/admin/category/update",
            type: "PUT",
            data: JSON.stringify(updateData),
            contentType: 'application/json; charset=utf-8',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (response) {
                if (response.status === 200) {
                    alert("카테고리가 수정되었습니다.");
                    $("#updateCategoryModal").modal("hide");
                    location.reload();
                } else {
                    alert("카테고리 수정에 실패했습니다.");
                    console.log(response.status);
                    console.log(response.data);
                }
            },
            error: function (xhr, status, error) {
                console.error("AJAX Error : ", status, error);
                console.error("Response : ", xhr.responseText);
                alert("수정 중 오류가 발생했습니다.");
            }
        });
    });

    $("#deleteMemberButton").click(function () {

        let selectMembers = [];

        $(".memberCheckbox:checked").each(function () {
            selectMembers.push(Number($(this).val()));
        });

        if (selectMembers.length === 0) {
            alert("삭제할 회원을 선택해 주세요!")
            return;
        }

        $.ajax({
            url: "/api/admin/member/inactive",
            type: "POST",
            data: JSON.stringify(selectMembers),
            contentType: 'application/json; charset=utf-8',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (response) {
                if (response.status === 200) {
                    alert("회원 상태를 비활성화 했습니다.");
                    location.reload();
                }else {
                    alert("회원 비활성화에 실패했습니다.");

                    console.log(response.status);
                    console.log(response.data);
                }
            },
            error: function (xhr) {
                console.log(xhr.responseText);
                alert("비활성화 중 오류가 발생했습니다.");
            }
        });
    });

    $("#deleteLessonButton").click(function () {
        let selectLessons = [];

        $(".lessonCheckbox:checked").each(function() {
            selectLessons.push(Number($(this).val()));
        });

        if (selectLessons.length === 0) {
            alert("삭제할 강의를 선택해 주세요!");
            return;
        }

        $.ajax({
            url: "/api/admin/lesson/delete",
            type: "POST",
            data: JSON.stringify(selectLessons),
            contentType: 'application/json; charset=utf-8',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (response) {
                if (response.status === 200) {
                    alert("강의를 삭제했습니다.")
                    location.reload();
                } else {
                    alert("강의 삭제에 실패했습니다.");
                    console.log(response.status);
                    console.log(response.data);
                }
            },
            error: function (xhr, status, error) {
                console.error("AJAX Error : ", status, error);
                console.error("Response : ", xhr.responseText);
                alert("삭제중 오류가 발생하였습니다.");
            }
        });
    });

    $("#deleteBoardButton").click(function () {
        let selectBoards = [];

        $(".boardCheckbox:checked").each(function() {
            selectBoards.push(Number($(this).val()));
        });

        if (selectBoards.length === 0) {
            alert("삭제할 게시글을 선택해 주세요!");
            return;
        }

        $.ajax({
            url: "/api/admin/board/delete",
            type: "POST",
            data: JSON.stringify(selectBoards),
            contentType: 'application/json; charset=utf-8',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (response) {
                if (response.status === 200) {
                    alert("게시글을 삭제했습니다.")
                    location.reload();
                } else {
                    alert("게시글 삭제에 실패했습니다.");
                    console.log(response.status);
                    console.log(response.data);
                }
            },
            error: function (xhr, status, error) {
                console.error("AJAX Error : ", status, error);
                console.error("Response : ", xhr.responseText);
                alert("삭제중 오류가 발생하였습니다.");
            }
        });
    });

    $("#deleteApplyButton").click(function () {
        let applyId = $("#applyId").val();

        console.log(applyId);

        $.ajax({
            url: "/api/admin/member/reject",
            type: "POST",
            data: JSON.stringify(applyId),
            contentType: 'application/json; charset=utf-8',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (response) {
                if (response.status === 200) {
                    alert("신청이 거절되었습니다.");
                    location.reload();
                } else {
                    alert("신청 거절에 실패했습니다.");
                    console.log(response.status);
                    console.log(response.data);
                }
            },
            error: function (xhr, status, error) {
                console.error("AJAX Error : ", status, error);
                console.error("Response : ", xhr.responseText);
                alert("거절 중 오류가 발생했습니다.");
            }
        })
    })

    $("#deleteCategoryButton").click(function () {
        let selectCategories = [];

        $(".categoryCheckbox:checked").each(function() {
            selectCategories.push(Number($(this).val()));
        });

        console.log(selectCategories);

        if (selectCategories.length === 0) {
            alert("삭제할 카테고리를 선택해 주세요!");
            return;
        }

        $.ajax({
            url: "/api/admin/category/delete",
            type: "POST",
            data: JSON.stringify(selectCategories),
            contentType: 'application/json; charset=utf-8',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (response) {
                if (response.status === 200) {
                    alert("카테고리를 삭제했습니다.")
                    location.reload();
                } else {
                    alert("카테고리 삭제에 실패했습니다.");
                    console.log(response.status);
                    console.log(response.data);
                }
            },
            error: function (xhr, status, error) {
                console.error("AJAX Error : ", status, error);
                console.error("Response : ", xhr.responseText);
                alert("삭제중 오류가 발생하였습니다.");
            }
        });
    });
});