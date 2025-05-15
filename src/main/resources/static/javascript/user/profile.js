  function previewImage(event) {
    const reader = new FileReader();
    reader.onload = function(e) {
      document.getElementById('profile-preview').src = e.target.result;
    };
    reader.readAsDataURL(event.target.files[0]);
  }