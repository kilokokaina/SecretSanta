function createSession() {
    let sessionName = document.querySelector('#session-name-input').value;
    fetch('new_session', {
        method: 'POST',
        body: sessionName
    }).then(async response => {
        let sessionItem = await response.json();
        console.log(sessionItem);

        document.querySelector('.session-list').innerHTML += `
            <div class="session-item">
                <div class="title">${sessionItem.sessionName}</div>
                <div class="status">${sessionItem.sessionName}</div>
                <button class="btn btn-success">Посмотреть</button>
            </div>
        `
    });
}