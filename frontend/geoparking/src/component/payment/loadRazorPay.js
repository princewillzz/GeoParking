export function loadRazorPay(src) {
	return new Promise((resolve) => {
		const script = document.createElement("script");
		script.src = src;
		document.body.appendChild(script);
		script.onload = () => {
			resolve(true);
		};
		script.onerror = () => {
			resolve(false);
		};
	});
}
