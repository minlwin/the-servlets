document.addEventListener('DOMContentLoaded', () => {
	document.getElementById('logoutBtn').addEventListener('click', () => {
		document.getElementById('logoutForm').submit()
		return false
	})
})