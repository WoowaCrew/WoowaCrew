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
        alert('오류가 발생했습니다.')
        window.location.href = origin + "/articles/anonymous"
      });

  const editButton = document.getElementById('article-edit-button')
  editButton.addEventListener('click', () => {
    let signingKey = "";
    while (signingKey.length < 8) {
      signingKey = prompt("비밀번호를 입력해 주세요?(8자리 이상)");
    }

    const formData = new FormData()
    formData.append('signingKey', signingKey)

    fetch(origin + "/api/articles/anonymous/" + articleId + "/check", {
      method: 'POST',
      body: formData
    }).then(response => {
      if(!response.ok) {
        throw Error(response);
      }
      window.location.href = origin + "/articles/anonymous/" + articleId + "/edit"
    }).catch(error => alert('잘못된 비밀번호입니다.'))
  })

  const deleteButton = document.getElementById('article-delete-button')
  deleteButton.addEventListener('click', () => {
    let signingKey = "";
    while (signingKey.length < 8) {
      signingKey = prompt("비밀번호를 입력해 주세요?(8자리 이상)");
    }

    const formData = new FormData()
    formData.append('signingKey', signingKey)

    fetch(origin + "/api/articles/anonymous/" + articleId + "/delete", {
      method: 'PUT',
      body: formData
    })
    .then(response => {
        if(!response.ok) {
            throw Error(response);
        }
        window.location.href = origin + '/articles/anonymous'
    })
    .catch(error => {
        alert("잘못된 비밀번호입니다.");
    })
  })
}

anonymousArticle()
