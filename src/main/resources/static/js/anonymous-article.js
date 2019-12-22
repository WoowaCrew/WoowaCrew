function anonymousArticle() {
  const articleUrl = window.location.href.split("/")
  const articleId = articleUrl[articleUrl.length - 1]
  const origin = window.location.origin

  fetch(origin + "/api/articles/anonymous/" + articleId, {
    method: 'GET'
  }).then(response => response.json())
      .then(article => {
        const articleTitle = document.getElementById('article-title')

        articleTitle.innerHTML += article.title

        const viewer = tui.Editor.factory({
          el: document.querySelector('#viewerSection'),
          viewer: true,
          height: '100%',
          initialValue: article.content
        })
      })
      .catch(error => {
        console.log(error)
        alert('오류가 발생했습니다.')
      });

  const editButton = document.getElementById('article-edit-button')
  editButton.addEventListener('click', () => window.location.href = origin + "/articles/anonymous/" + articleId + "/edit")

  const deleteButton = document.getElementById('article-delete-button')
  deleteButton.addEventListener('click', () => {
    let password = "";
    while (password.length < 8) {
      password = prompt("비밀번호를 입력해 주세요?(8자리 이상)");
    }

    const formData = new FormData()
    formData.append('password', password)

    fetch(origin + "/api/articles/anonymous/" + articleId, {
      method: 'PUT',
      body: formData
    })
    .then(() => window.location.href = origin + '/articles/anonymous')
    .catch(error => {
        alert("잘못된 비밀번호입니다.");
    })
  })
}

anonymousArticle()
