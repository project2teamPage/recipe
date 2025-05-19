document.addEventListener("DOMContentLoaded", function () {
    const slides = document.querySelectorAll(".step-wrapper");
    const prevBtn = document.querySelector(".recipe-slider .prev");
    const nextBtn = document.querySelector(".recipe-slider .next");

    let currentIndex = 0;

    if (!slides.length) return;

    function showSlide(index) {
        slides.forEach((slide, i) => {
            slide.classList.remove("active");
            if (i === index) {
                slide.classList.add("active");
            }
        });
    }

    prevBtn.addEventListener("click", () => {
        currentIndex = (currentIndex - 1 + slides.length) % slides.length;
        showSlide(currentIndex);
    });

    nextBtn.addEventListener("click", () => {
        currentIndex = (currentIndex + 1) % slides.length;
        showSlide(currentIndex);
    });

    // 초기 표시
    showSlide(currentIndex);
});

/* 모달창 js */
document.addEventListener('DOMContentLoaded', function () {
    const modal = document.getElementById('reportModal');
    const span = document.querySelector('.close');
    const form = document.getElementById('reportForm');

    // 모달 열기 함수 (전역으로 노출)
    window.openReportModal = function(btn) {
        const modal = document.getElementById('reportModal');

        const targetTypeDisplay = document.querySelector('.target-type-display');
        const targetNickNameDisplay = document.querySelector('.target-nickname-display');

        if (btn) {
            const type = btn.getAttribute('data-target-type') || 'RECIPE_POST';
            const nickname = btn.getAttribute('data-target-nickname') || '';
            const id = btn.getAttribute('data-target-id') || '';

            if (targetTypeDisplay) {
                targetTypeDisplay.innerText = type === 'RECIPE_COMMENT' ? '레시피 댓글' : '레시피 게시글';
            }
            if (targetNickNameDisplay) {
                targetNickNameDisplay.innerText = nickname;
            }

            document.getElementById('targetId').value = id;
            document.getElementById('targetNickName').value = nickname;
            document.getElementById('targetType').value = type;

        } else {
            const reportBtn = document.getElementById('reportBtn');

            const type = reportBtn.getAttribute('data-target-type') || 'RECIPE_POST';
            const nickname = reportBtn.getAttribute('data-target-nickname') || '';
            const id = reportBtn.getAttribute('data-target-id') || '';

            if (targetTypeDisplay) {
                targetTypeDisplay.innerText = type === 'RECIPE_COMMENT' ? '레시피 댓글' : '레시피 게시글';
            }
            if (targetNickNameDisplay) {
                targetNickNameDisplay.innerText = nickname;
            }

            document.getElementById('targetId').value = id;
            document.getElementById('targetNickName').value = nickname;
            document.getElementById('targetType').value = type;
        }

        modal.style.display = 'block';
    };


    // 모달 닫기 (X 버튼)
    span.onclick = function() {
        modal.style.display = 'none';
    };

    // 모달 바깥 클릭 시 닫기
    window.onclick = function(event) {
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    };

    // 신고 폼 제출
    form.addEventListener('submit', function(event) {
        event.preventDefault();

        const userIdElem = document.getElementById('userId');
        const userNickNameElem = document.getElementById('userNickName');

        if (!userIdElem || !userIdElem.value) {
            alert("로그인 후 신고하실 수 있습니다.");
            return;
        }

        const data = {
            targetId: document.getElementById('targetId').value,
            targetNickName: document.getElementById('targetNickName').value,
            targetType: document.getElementById('targetType').value,
            title: document.getElementById('report-title').value,
            reason: document.getElementById('report-reason').value,
            userId: userIdElem.value,
            userNickName: userNickNameElem.value
        };

        const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
        const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

        fetch('/report', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [csrfHeader]: csrfToken,
            },
            body: JSON.stringify(data)
        })
        .then(response => {
            if (response.ok) {
                alert('신고가 접수되었습니다.');
                modal.style.display = 'none';
                form.reset();
            } else {
                alert('신고 접수에 실패했습니다.');
            }
        })
        .catch(error => {
            alert('오류가 발생했습니다.');
            console.error(error);
        });
    });
});
