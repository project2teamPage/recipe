
  let stepCount = 0;

  // 초기 3단계 렌더링
  window.onload = function () {
    for (let i = 0; i < 3; i++) {
      addStep();
    }
  };

  function addStep() {
    const container = document.getElementById("stepsContainer");

    const stepDiv = document.createElement("div");
    stepDiv.className = "recipe-step";
    stepDiv.innerHTML = `
      <h4>STEP ${stepCount + 1}</h4>
      <div>
        <label>이미지:</label>
        <input type="file" name="recipeStepDtoList[${stepCount}].imageFile" accept="image/*" />
      </div>
      <div>
        <label>제목:</label>
        <input type="text" name="recipeStepDtoList[${stepCount}].title" placeholder="예: 양파 볶기" />
      </div>
      <div>
        <label>내용:</label>
        <textarea name="recipeStepDtoList[${stepCount}].description" placeholder="설명을 입력하세요."></textarea>
      </div>
      <hr/>
    `;
    container.appendChild(stepDiv);
    stepCount++;
  }
