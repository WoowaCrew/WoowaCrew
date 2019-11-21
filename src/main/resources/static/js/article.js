function article() {
  const initValue = document.getElementById('content').value
  const viewer = tui.Editor.factory({
    el: document.querySelector('#viewerSection'),
    viewer: true,
    height: '100%',
    initialValue: initValue
  })
}

article()