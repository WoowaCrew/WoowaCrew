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

document.getElementById('save-button').addEventListener('click', function (e) {
  if (document.getElementById('title').value.trim() === "") {
    alert("제목을 입력해주세요")
    return
  }
  if (document.getElementById('article-contents').value.trim() === "") {
    alert("본문을 입력해주세요")
    return
  }
  document.getElementById('article-edit').submit();
})
