function anonymousArticleEdit() {
  const origin = window.location.origin
  const articleUrl = window.location.href.split("/")
  const articleId = articleUrl[articleUrl.length - 2]

  fetch(origin + "/api/articles/anonymous/" + articleId, {
    method: 'GET'
  }).then(response => response.json())
    .then(article => {
      const articleTitle = document.getElementById('title')

      articleTitle.value = article.title

      const editor = new tui.Editor({
        el: document.querySelector('#editSection'),
        initialEditType: 'markdown',
        previewStyle: 'vertical',
        events: {
          change: function () {
            document.getElementById('article-contents').setAttribute('value', editor.getMarkdown())
          }
        },
        height: 'calc(100vh - 150px)',
        initialValue: article.content
      })

    })
    .catch(error => {
      console.log(error)
      alert('오류가 발생했습니다.')
      window.location.href = origin + "/articles/anonymous"
    })

  document.getElementById('article-update-button').addEventListener('click', function (e) {
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

    let password = "";
    while (password.length < 8) {
      password = prompt("비밀번호를 입력해 주세요?(8자리 이상)");
    }

    const formData = new FormData()
    formData.append('articleId', articleId)
    formData.append('title', title)
    formData.append('content', content)
    formData.append('password', password)

    fetch(origin + "/api/articles/anonymous/" + articleId, {
      method: 'PUT',
      body: formData
    }).then(response => {
      if(!response.ok) {
        throw Error(response);
      }
      window.location.href = origin + "/articles/anonymous"
    }).catch(error => alert('잘못된 비밀번호입니다.'));
  })
}

anonymousArticleEdit()