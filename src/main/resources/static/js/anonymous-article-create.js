function anonymousArticleCreate() {
  const editor = new tui.Editor({
    el: document.querySelector('#editSection'),
    initialEditType: 'markdown',
    previewStyle: 'vertical',
    events: {
      change: function () {
        document.getElementById('article-contents').setAttribute('value', editor.getMarkdown())
      }
    },
    height: 'calc(100vh - 150px)'
  });

  const origin = window.location.origin

  document.getElementById('article-save-button').addEventListener('click', function (e) {
    const title = document.getElementById('title').value;
    const content = document.getElementById('article-contents').value;
    const password = document.getElementById('password').value.trim();

    if (title.trim() === "") {
      alert("제목을 입력해주세요")
      return
    }
    if (content.trim() === "") {
      alert("본문을 입력해주세요")
      return
    }

    if (password === "" || password.length < 8) {
      alert("8자리 이상의 비밀번호를 입력해주세요.")
      return
    }

    const formData = new FormData()
    formData.append('title', title)
    formData.append('content', content)
    formData.append('password', password)

    fetch(origin + "/api/articles/anonymous", {
      method: 'POST',
      body: formData
    }).then(function (response) {
      window.location.href = "/articles/anonymous"
    }).catch(error => alert('오류가 발생했습니다.'));
  })
}

anonymousArticleCreate()