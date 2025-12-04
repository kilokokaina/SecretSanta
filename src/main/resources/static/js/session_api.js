function createSession() {
    let sessionName = document.querySelector('#session-name-input').value;
    fetch('new_session', {
        method: 'POST',
        body: sessionName
    }).then(async response => {
        let sessionItem = await response.json();
        console.log(sessionItem);
    });
}