function articleEdit() {
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

  document.getElementById('save-button').addEventListener('click', function (e) {
    const title = document.getElementById('title').value;
    const content = document.getElementById('article-contents').value;

    if (title.trim() === "") {
      alert("제목을 입력해주세요")
      return
    }
    if (content.trim() === "") {
      alert("본문을 입력해주세요")
      return
    }

    const formData = new FormData()
    formData.append('title', title)
    formData.append('content', content)

    fetch(origin + "/api/articles", {
      method: 'POST',
      body: formData
    }).then(function (response) {
      window.location.href = response.headers.get('Location')
    }).catch(error => alert('오류가 발생했습니다.'));
  })
}

articleEdit()