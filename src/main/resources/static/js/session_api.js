function createSession() {
    let sessionName = document.querySelector('#session-name-input').value;
    fetch('new_session', {
        method: 'POST',
        body: sessionName
    }).then(async response => {
        let sessionItem = await response.json();
        console.log(sessionItem);

        let sessionList = document.querySelector('.admin-session-list').innerHTML;
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
        let sessionResponse = await response.json();
        console.log(sessionResponse);

        let adminList = sessionResponse.admin;
        let participantList = sessionResponse.user;


        let adminListHTML = document.querySelector('.admin-session-list').innerHTML;
        adminList.forEach(adminItem => {
            adminListHTML += `
                <div class="session-item">
                    <div class="title">${adminItem.sessionName}</div>
                    <div class="status">${adminItem.status}</div>
                    <button class="btn btn-success">Посмотреть</button>
                </div>
            `;
        });
        let participantListHTML = document.querySelector('.participant-session-list').innerHTML;
        participantList.forEach(participantItem => {
            participantListHTML += `
                <div class="session-item">
                    <div class="title">${participantItem.sessionName}</div>
                    <div class="status">${participantItem.status}</div>
                    <button class="btn btn-success">Посмотреть</button>
                </div>
            `;
        });

        document.querySelector('.admin-session-list').innerHTML = adminListHTML;
        document.querySelector('.participant-session-list').innerHTML = participantListHTML;
    });
}