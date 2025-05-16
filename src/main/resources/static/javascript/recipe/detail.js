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
    const btn = document.getElementById('reportBtn');
    const span = document.querySelector('.close');
    const form = document.getElementById('reportForm');

    btn.onclick = function() {
        modal.style.display = 'block';
    };

    span.onclick = function() {
        modal.style.display = 'none';
    };

    window.onclick = function(event) {
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    };

    form.addEventListener('submit', function(event) {
        event.preventDefault();

        const userIdElem = document.getElementById('userId');
        const userNickNameElem = document.getElementById('userNickName');

            if (!userIdElem) {
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

        console.log("신고 전송 데이터 확인:", data); // ✅ 추가

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
