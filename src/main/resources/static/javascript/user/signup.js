


window.onload = () => {
// 체크박스
document.addEventListener("DOMContentLoaded", function () {
  const form = document.querySelector(".sign-up-form");
  let isIdChecked = false;
  let isEmailChecked = false;

  form.addEventListener("submit", function (e) {
    const checks = document.querySelectorAll(".required-check input[type='checkbox']");
    let allChecked = true;

    checks.forEach(check => {
      if (!check.checked) {
        allChecked = false;
      }
    });

    if (!allChecked) {
      e.preventDefault();
      alert("모든 필수 항목에 동의해야 다음 단계로 이동할 수 있습니다.");
      return;
    }

    if (!isIdChecked) {
      e.preventDefault();
      alert("아이디 중복 확인을 완료해주세요.");
      return;
    }

    if (!isEmailChecked) {
      e.preventDefault();
      alert("이메일 중복 확인을 완료해주세요.");
      return;
    }
  });

  // 아이디 중복 확인
  window.checkLoginId = function () {
    const loginId = document.getElementById("checkbtn").value;

    if (!loginId.trim()) {
      alert("아이디를 입력해주세요.");
      return;
    }

    fetch(`/user-loginId/${loginId}/exists`)
      .then(res => res.json())
      .then(isDuplicate => {
        const resultSpan = document.getElementById("loginIdResult");
        if (isDuplicate) {
          alert("이미 사용 중인 아이디입니다.");
          resultSpan.textContent = "이미 사용 중인 아이디입니다.";
          resultSpan.style.color = "red";
          isIdChecked = false;
        } else {
          resultSpan.textContent = "사용 가능한 아이디입니다.";
          resultSpan.style.color = "green";
          isIdChecked = true;
        }
      })
      .catch(() => {
        alert("아이디 중복 확인 중 오류가 발생했습니다.");
      });
  };

  // 이메일 중복 확인
  window.checkEmail = function () {
    const email = document.getElementById("email").value;

    if (!email.trim()) {
      alert("이메일을 입력해주세요.");
      return;
    }

    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(email)) {
      alert("올바른 이메일 형식이 아닙니다.");
      return;
    }

    fetch(`/user-email/${email}/exists`)
      .then(res => res.json())
      .then(isDuplicate => {
        const resultSpan = document.getElementById("emailResult");
        if (isDuplicate) {
          alert("이미 사용 중인 이메일입니다.");
          resultSpan.textContent = "이미 사용 중인 이메일입니다.";
          resultSpan.style.color = "red";
          isEmailChecked = false;
        } else {
          resultSpan.textContent = "사용 가능한 이메일입니다.";
          resultSpan.style.color = "green";
          isEmailChecked = true;
        }
      })
      .catch(() => {
        alert("이메일 중복 확인 중 오류가 발생했습니다.");
      });
  };
});




// 비밀번호 체크
    function checkPasswordMatch() {
        const password = document.getElementById("password").value;
        const confirm = document.getElementById("confirmPassword").value;
        const result = document.getElementById("passwordMatchResult");

        if (!confirm) {
          result.innerText = "";
          return;
        }

        if (password === confirm) {
          result.innerText = "비밀번호가 일치합니다.";
          result.style.color = "green";
        } else {
          result.innerText = "비밀번호가 일치하지 않습니다.";
          result.style.color = "red";
        }
      }


// 다음단계로 넘어가기
document.addEventListener("DOMContentLoaded", function () {
  const form = document.querySelector(".sign-up-form");

  form.addEventListener("submit", function (e) {
    const checks = document.querySelectorAll(".required-check input[type='checkbox']");
    let allChecked = true;

    checks.forEach(check => {
      if (!check.checked) {
        allChecked = false;
      }
    });

    if (!allChecked) {
      e.preventDefault(); // 제출 방지`
      alert("모든 필수 항목에 동의해야 다음 단계로 이동할 수 있습니다.");
    }
  });
});

}
