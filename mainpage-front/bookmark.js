const itemsPerPage = 6;
let currentPage = 1;

function showPage(page) {
  const items = document.querySelectorAll(".checkbox-wrapper");
  const start = (page - 1) * itemsPerPage;
  const end = page * itemsPerPage;

  items.forEach((item, index) => {
    item.style.display = index >= start && index < end ? "flex" : "none";
  });

  currentPage = page;
}

function nextPage() {
  const totalItems = document.querySelectorAll(".checkbox-wrapper").length;
  const totalPages = Math.ceil(totalItems / itemsPerPage);
  if (currentPage < totalPages) showPage(currentPage + 1);
}

function prevPage() {
  if (currentPage > 1) showPage(currentPage - 1);
}

window.onload = () => {
  showPage(1); // 첫 페이지 로드 시 실행
};
