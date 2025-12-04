function createSession() {
    let sessionName = document.querySelector('#session-name-input').value;
    fetch('new_session', {
        method: 'POST',
        body: sessionName
    }).then(async response => {
        let sessionItem = await response.json();
        console.log(sessionItem);

        let sessionList = document.querySelector('.session-list').innerHTML;
        sessionList = `
            <div class="session-item">
                <div class="title">${sessionItem.sessionName}</div>
                <div class="status">${sessionItem.sessionStatus}</div>
                <button class="btn btn-success">Посмотреть</button>
            </div>
        ` + sessionList;
        document.querySelector('.session-list').innerHTML = sessionList;
    });
}

function getSessions() {
    fetch('get_sessions').then(async response => {
        let sessionList = await response.json();
        console.log(sessionList);

        let sessionListHTML = document.querySelector('.session-list').innerHTML;
        sessionList.forEach(sessionItem => {
            sessionListHTML += `
                <div class="session-item">
                    <div class="title">${sessionItem.sessionName}</div>
                    <div class="status">${sessionItem.sessionStatus}</div>
                    <button class="btn btn-success">Посмотреть</button>
                </div>
            `;
        });
        document.querySelector('.session-list').innerHTML = sessionListHTML;
    });
}