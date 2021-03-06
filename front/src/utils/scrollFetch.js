import { throttling } from './throttling.js';

const throttler = throttling();

function scrollFetch(fetchData, element) {
    element.addEventListener('scroll', () => {
        throttler.throttle(() => {
            var dh = element.scrollHeight;
            var dch = element.clientHeight;
            var dct = element.scrollTop;

            if (dh - (dch + dct) > 1) return;
            fetchData();
        }, 700);
    });
}

function reverseScrollFetch(fetchData, element) {
  element.addEventListener('scroll', () => {
      throttler.throttle(() => {
          if (element.scrollTop < 50) {
            fetchData();
          }
          
      }, 700);
  });
}

export { scrollFetch, reverseScrollFetch };