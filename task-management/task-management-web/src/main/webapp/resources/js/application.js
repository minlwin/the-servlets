document.addEventListener('DOMContentLoaded', () => {
	if(document.getElementById('logoutBtn')) {
		document.getElementById('logoutBtn').addEventListener('click', () => {
			document.getElementById('logoutForm').submit()
			return false
		})
	}
})