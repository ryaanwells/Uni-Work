from django.conf.urls import patterns, url

from rango import views

urlpatterns = patterns ('', url(r'^$',views.index,name='index'),
                        url(r'^cat/(?P<category_name_url>\w+)', views.category, name='category'),
                        url(r'^cat_add/$', views.add_category, name='add_category'),
                        url(r'^page_add/(?P<category_name_url>\w+)', views.add_page, name='add_page'),
)
