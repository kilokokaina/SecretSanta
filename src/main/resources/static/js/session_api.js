let joinSessionModal = new bootstrap.Modal(document.getElementById('joinSessionModal'));
let joinSessionBodyModal = new bootstrap.Modal(document.getElementById('joinSessionModalBody'));

function createSession() {
    let sessionName = document.querySelector('#session-name-input').value;
    fetch('/new_session', {
        method: 'POST',
        body: sessionName
    }).then(async response => {
        let sessionItem = await response.json();
        console.log(sessionItem);

        let sessionList = document.querySelector('.admin-session-list').innerHTML;
        sessionList = `
            <div class="session-item">
                <div class="title">${sessionItem.sessionName}</div>
                <div class="status">${sessionItem.status}</div>
                <button class="btn btn-success" onclick="location.href='session/${sessionItem.sessionId}'">Посмотреть</button>
            </div>
        ` + sessionList;
        document.querySelector('.admin-session-list').innerHTML = sessionList;
    });
}

function getSessions() {
    fetch('/get_sessions').then(async response => {
        let sessionResponse = await response.json();
        console.log(sessionResponse);

        let adminList = sessionResponse.admin;
        let participantList = sessionResponse.user;

        let adminListHTML = document.querySelector('.admin-session-list').innerHTML;
        if (adminList.length > 0) adminListHTML = '';
        adminList.forEach(adminItem => {
            let sessionStatus = (adminItem.status) ? '🔓' : '🔒';
            adminListHTML += `
                <div class="session-item">
                    <div class="title">${adminItem.sessionName}</div>
                    <div class="status">${sessionStatus}</div>
                    <button class="btn btn-success" onclick="location.href='session/${adminItem.sessionId}'">Посмотреть</button>
                </div>
            `;
        });

        let participantListHTML = document.querySelector('.participant-session-list').innerHTML;
        if (participantList.length > 0) participantListHTML = '';
        participantList.forEach(participantItem => {
            let sessionStatus = (participantItem.status) ? '🔓' : '🔒';
            participantListHTML += `
                <div class="session-item">
                    <div class="title">${participantItem.sessionName}</div>
                    <div class="status">${sessionStatus}</div>
                    <button class="btn btn-success" onclick="location.href='session/${participantItem.sessionId}'">Посмотреть</button>
                </div>
            `;
        });

        document.querySelector('.admin-session-list').innerHTML = adminListHTML;
        document.querySelector('.participant-session-list').innerHTML = participantListHTML;
    });
}

function findSession() {
    let sessionId = document.querySelector('#session-id-input').value;
    fetch(`/get_sessions/${sessionId}`).then(async response => {
        if (response.status === 200) {
            joinSessionModal.hide()
            joinSessionBodyModal.show();
        }
    })
}

function joinSession() {
    let sessionBody = {
        sessionId: document.querySelector('#session-id-input').value,
        userNickname: document.querySelector('#userNickname').value,
        wishList: document.querySelector('#wishList').value,
        stopList: document.querySelector('#stopList').value,
    };

    fetch('/join_session', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(sessionBody)
    }).then(async response => {
        let sessionItem = await response.json();
        console.log(sessionItem);

        let sessionList = document.querySelector('.participant-session-list').innerHTML;
        sessionList = `
            <div class="session-item">
                <div class="title">${sessionItem.sessionName}</div>
                <div class="status">${sessionItem.status}</div>
                <button class="btn btn-success" onclick="location.href='session/${sessionItem.sessionId}'">Посмотреть</button>
            </div>
        ` + sessionList;
        document.querySelector('.participant-session-list').innerHTML = sessionList;
    });
}

function changeStatus() {
    let sessionId = document.querySelector('#session-id').innerHTML;
    fetch(`/change_status/${sessionId}`, { method: 'PUT' }).then(async response => {
        if (response.status === 200) location.reload();
    })
}

async function copyToClipboard() {
    let sessionId = document.querySelector('#session-id').innerHTML;
    const type = "text/plain";
    const clipboardItemData = {
        [type]: sessionId,
    };
    const clipboardItem = new ClipboardItem(clipboardItemData);
    await navigator.clipboard.write([clipboardItem]);
}