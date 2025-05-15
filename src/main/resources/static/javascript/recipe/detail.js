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
