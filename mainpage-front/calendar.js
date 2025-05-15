function toggleLike(button) {
  const icon = button.querySelector("i");

  // 상태 토글
  button.classList.toggle("liked");

  // 아이콘 클래스 전환
  if (icon.classList.contains("fa-regular")) {
    icon.classList.remove("fa-regular");
    icon.classList.add("fa-solid");
  } else {
    icon.classList.remove("fa-solid");
    icon.classList.add("fa-regular");
  }
}
