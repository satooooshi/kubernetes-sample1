export default function({ $axios }) {
  $axios.onRequest(config => {
    console.log(`Making request to ${config.url}`);
  });
  $axios.onError(error => {
    const code = parseInt(error.response && error.response.status);
    console.log(`ERROR: status: ${code}`);
  });
}