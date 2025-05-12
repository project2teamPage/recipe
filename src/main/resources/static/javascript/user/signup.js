  document.getElementById("submitBtn").addEventListener("submit", function(e) {
    const checks = document.querySelectorAll(".required-check");
    let allChecked = true;

    checks.forEach(check => {
      if (!check.checked) {
        allChecked = false;
      }
    });

    if (!allChecked) {
      e.preventDefault(); // 제출 or 이동 막기
      alert("모든 필수 항목에 동의해야 다음 단계로 이동할 수 있습니다.");
    }
  });