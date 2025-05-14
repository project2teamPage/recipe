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




        const date = new Date();
let originalDate = date.getDate();

let year = date.getFullYear();
let month = date.getMonth();

const prevMonth = document.getElementById("prev-month");
const prevYear = document.getElementById("prev-year");
const nextMonth = document.getElementById("next-month");
const nextYear = document.getElementById("next-year");

const resetDate = document.getElementById("reset-date");

resetDate.addEventListener("click", () => {
  const date = new Date();

  const year = date.getFullYear();
  const month = date.getMonth();

  renderCalendar(year, month);
});

prevMonth.addEventListener("click", () => {
  if (month === 0) {
    month = 11;
    year--;
  } else {
    month--;
  }
  const originalDate = date.getDate();
  const maxDate = new Date(year, month, 0).getDate();
  renderCalendar(
    year,
    month,
    date.getDate(),
    originalDate > maxDate ? maxDate : originalDate
  );
});

nextMonth.addEventListener("click", () => {
  if (month === 11) {
    month = 0;
    year++;
  } else {
    month++;
  }
  const maxDate = new Date(year, month + 1, 0).getDate();
  renderCalendar(
    year,
    month,
    date.getDate(),
    originalDate > maxDate ? maxDate : originalDate
  );
});

resetDate.innerText = date.toLocaleDateString();

const renderCalendar = (year, month, date) => {
  console.log(new Date(year, month + 1, 0).getDate());
  // 어떤 년도에, 어떤 달에, 0번째 인덱스를 알려줌 (저번달)

  const prevDayIndex = new Date(year, month, 0).getDay(); // 이전달 마지막 요일 구하기

  // const days = [0,1,2,3,4,5,6];
  const days = 7;

  const prevs = prevDayIndex + 1 === 7 ? 0 : prevDayIndex + 1;

  // 마지막 날짜 구하기

  const lastDayIndex = new Date(year, month + 1, 0).getDay(); // !이번달 마지막 요일 구하기
  console.log(lastDayIndex);

  // const week = ['일','월','화','수','목','금','토' ]
  const lasts = 6 - lastDayIndex;
  console.log(lasts);

  const maxDate = new Date(year, month + 1, 0).getDate(); // !이번달 며칠까지 있는지 구하기

  const calendar = document.getElementById("calendar");

  // calendar.style.border = "1px solid";

  calendar.innerHTML = null;
  // 초기화하고 새로 그려주기

  const prevArray = Array.from({ length: prevs }); // 이전달 공백
  const lastArray = Array.from({ length: lasts }); // 이번달 공백

  const item = `<button class="border size full flex flex-col">
    <p class ="text-right p-2"></p>
    </button>`;

  // calendar  의 넓이 -14 px(border값) 후에 나누기 7한 값
  const width = (calendar.clientWidth - 14) / 7;
  console.log((width - 14) / 7);

  prevArray.forEach(() => {
    const li = document.createElement("li");
    li.className = `size-[${width}px] bg-gray-50 hover:bg-gray-200`;
    calendar.appendChild(li);
  });

  for (let i = 0; i < maxDate; i++) {
    const li = document.createElement("li");
    li.className = `size-[${width}px]`;
    const isToday = i + 1 === date;
    // 오늘 날짜 색 변경 하는법
    li.innerHTML = `<button class="border size-full flex flex-col ${
      isToday ? " bg-blue-50" : ""
    }">
    <p class ="text-right p-2">${i + 1}</p>
    </button>`;

    li.addEventListener("click", () => {
      originalDate = i+1;
      renderCalendar(year, month, i+1);
    });

    calendar.appendChild(li);
  }

  lastArray.forEach(() => {
    const li = document.createElement("li");
    li.className = `size-[${width}px] bg-gray-50 hover:bg-gray-200`;
    calendar.appendChild(li);
  });

  resetDate.innerText = new Date(
    year,
    month,
    originalDate
  ).toLocaleDateString();
};

renderCalendar(year, month, date.getDate());

window.addEventListener("resize", () => renderCalendar(year, month));
